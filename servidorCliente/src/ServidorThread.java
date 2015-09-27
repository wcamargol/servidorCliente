

import model.Serializador;
import model.PortaSerial;
import model.Mensagem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lubuntu
 */
public class ServidorThread implements Runnable{
    private DatagramSocket socket; //socket para conectar ao cliente
    private PortaSerial portaSerial;
    private DatagramPacket pctRecebido;
    private ServidorSerialThread sST;
    private Serializador sD;
    
    public ServidorThread(PortaSerial portaSerial){ 
        this.socket = null;
        this.pctRecebido = null;
        this.portaSerial = portaSerial;
        this.sD = new Serializador();
        this.sST = new ServidorSerialThread(portaSerial);
        try{		   
            //cria DatagramSocket para envio e recebimento de pacotes
            this.socket = new DatagramSocket(5000);
        }catch (SocketException ex){
            System.out.println("Não foi possível iniciar o servidor");
            ex.printStackTrace();
        }
    }
        
    public void esperaMsg() throws IOException, InterruptedException{
        //Mensagem mensagem = new Mensagem();
        String dispositivo, 
            comando;
        Mensagem msgCliente = null;
        String fbackDispo = null;
        while (true){
            byte data[] = new byte[ 1024 ]; // configura o pacote
            DatagramPacket pctRecebido =
                           new DatagramPacket( data, data.length );
            try{ //recebe o pacote, exibe o conteúdo, retorna uma cópia ao cliente
                socket.receive(pctRecebido); //espera receber o pacote
                msgCliente = (Mensagem)this.sD.deserialize(pctRecebido.getData());
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println("Problemas no recebimento da requisição do cliente");
                ex.printStackTrace();
            }
            comando = msgCliente.getMsg();           
            if (comando!=null){                
                dispositivo = comando.substring(0, 3);
                this.sST.pausarThread(true);
                this.portaSerial.escreverDados(comando);
                Thread.sleep(1000);
                fbackDispo = this.portaSerial.lerDados(); 
                this.sST.pausarThread(false);
            }
            
            msgCliente.setCodigo("F");
            msgCliente.setMsg(fbackDispo);
            feedBack(msgCliente, pctRecebido);
        } //fim do while
    } //fim do metodo esperaMsg
    
    private void feedBack(Mensagem msg, DatagramPacket dgp){
        byte[] data = new byte[1024];
        try {
            data = this.sD.serialize(msg);
        } catch (IOException ex) {
            System.out.println("Problemas para empacotar a mensagem");
            ex.printStackTrace();
        }
        DatagramPacket pctEnviar = null;
        pctEnviar = new DatagramPacket(data,
                                      data.length, 
                                      dgp.getAddress(), 
                                      dgp.getPort());
        try {
            socket.send(pctEnviar);
        } catch (IOException ex) {
            System.out.println("Problemas no envio da mensagem");
            ex.printStackTrace();
        }
    }
    
    private String executarCmd(String msg){
        this.portaSerial.escreverDados(msg);
        return this.portaSerial.lerDados();
    }
   
    @Override
    public void run() {
        try {
            esperaMsg();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

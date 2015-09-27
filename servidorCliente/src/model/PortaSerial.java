package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 *
 * @author lubuntu
 */
public class PortaSerial implements SerialPortEventListener {
    private CommPortIdentifier portaId;
    private SerialPort portaSerial;
    private BufferedReader entrada;
    private OutputStream saida;
    private String dadosRecebidos;
    public static final int DATA_RATE = 9600;
    
    
    public PortaSerial(String porta, int timeOut){
        try {
            this.portaId= CommPortIdentifier.getPortIdentifier(porta);
            this.portaSerial = (SerialPort)portaId.open(porta, timeOut);
            this.portaSerial.setSerialPortParams(DATA_RATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
            this.portaSerial.addEventListener(this);
            this.portaSerial.notifyOnDataAvailable(true);
        } catch (NoSuchPortException ex) {
            System.out.println("Porta não encontrada.");
            System.out.println("Erro: " + ex.toString());            
        } catch (PortInUseException ex) {
            System.out.println("Não foi possivel abrir a porta.");
            //System.out.println("Erro: " + ex.toString());
            ex.printStackTrace();
        } catch (UnsupportedCommOperationException | TooManyListenersException ex) {
            this.portaSerial.close();
            System.out.println("Problemas na configuração da porta.");
            System.out.println("Erro: " + ex.toString());        
        }
    }       
    
    public String lerDados(){
        return this.dadosRecebidos;
    }
    
    public void escreverDados(String dados){
        byte[] dado = new byte[1];
        String str;
        try {            
            this.saida = portaSerial.getOutputStream();
            this.saida.write(dados.getBytes());
            do{ 
                str = null;                 
                str = lerDados();
                if (str == null){
                    str = "_-_-";
                }
            }while(!str.substring(0, 4).equals(dados.substring(0, 4)));
        } catch (IOException ex) {
            System.out.println("Problemas na execução do comando.");
            ex.printStackTrace();
        }
        System.out.println("acabou");
    }
    
    public SerialPort getSerialPort(){
        return this.portaSerial;
    }
    
    public synchronized void fecharPorta() {
        if (this.portaSerial != null) {
            this.portaSerial.removeEventListener();
            this.portaSerial.close();
        }
    }
    
    @Override
    public synchronized void serialEvent(SerialPortEvent spe) {
        try{ 
            switch (spe.getEventType() ) {
                case SerialPortEvent.DATA_AVAILABLE: 
                    if (this.entrada == null) {
                        this.entrada = new BufferedReader(
                            new InputStreamReader(
                                    this.portaSerial.getInputStream()));
                    }
                    this.dadosRecebidos = this.entrada.readLine();
                    break; 
                default:
                    break;
            }
        }catch (Exception ex) {
            System.out.println("Problemas na leitura dos dados.");
            ex.printStackTrace();
        }
    }      
}

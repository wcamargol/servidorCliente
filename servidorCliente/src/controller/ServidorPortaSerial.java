/**
 * Código adaptado de http://pt.stackoverflow.com/questions/43127/multi-thread-em-socket-java
 */
package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import model.PortaSerial;

public class ServidorPortaSerial implements  Runnable{
    private final Socket connSocket;
    private static PortaSerial portaSerial;
    private static ClienteSupervisorio clienteSupervisorio;
    
    private ServidorPortaSerial(Socket cliente){
        this.connSocket = cliente;         
    }
    
    public static void main(String[] args){
        ServerSocket servidor = null;
        portaSerial = new PortaSerial("/dev/ttyUSB0", 2000);
        try {
            
            servidor = new ServerSocket (12345);
            clienteSupervisorio = new ClienteSupervisorio();
            Thread threadCliente = new Thread(clienteSupervisorio);
            threadCliente.start();
            while (true) {
                Socket socketCliente = null;
                socketCliente = servidor.accept();
                // Cria uma thread do servidor para tratar a conexão
                ServidorPortaSerial threadServidor = new ServidorPortaSerial(socketCliente);
                Thread thread = new Thread(threadServidor);                
                // Inicia a thread para o cliente conectado
                thread.start(); 
            }            
       }catch (IOException ex) {
            //portaSerial.fecharPorta();
            ex.printStackTrace();
        }
    }
    
    public void trataRequisicaoCliente(){
        
        Scanner vemCliente = null;
        PrintStream vaiCliente = null;
        String strEntrada, strSaida;
        try {
            vemCliente = new Scanner(this.connSocket.getInputStream());       
            vaiCliente = new PrintStream(this.connSocket.getOutputStream());        
            strEntrada = vemCliente.nextLine();
            System.out.println(strEntrada);
            if (!strEntrada.equals("?")){
                portaSerial.escreverDados(strEntrada);
            }
            strSaida = portaSerial.lerDados();
            System.out.println(strSaida);
            vaiCliente.println(strSaida);
            vemCliente.close();
            vaiCliente.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void run(){
        clienteSupervisorio.pausar();
        trataRequisicaoCliente();
        clienteSupervisorio.prosseguir();
    }
}

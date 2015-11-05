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
            Thread threadClienteSupervisorio = new Thread(clienteSupervisorio);
            threadClienteSupervisorio.start();
            
            while (true) {
                Socket socketCliente = null;
                socketCliente = servidor.accept();
                ServidorPortaSerial threadServidor = new ServidorPortaSerial(socketCliente);
                Thread thread = new Thread(threadServidor); 
                thread.start(); 
            }                 
       }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void trataRequisicaoCliente(){
        Scanner vemDoCliente = null;
        PrintStream vaiParaCliente = null;
        String vaiPortaSerial, vemPortaSerial = null;
        try {
            vemDoCliente = new Scanner(this.connSocket.getInputStream());             
            vaiParaCliente = new PrintStream(this.connSocket.getOutputStream());        
            vaiPortaSerial = vemDoCliente.nextLine();
            portaSerial.escreverDados(vaiPortaSerial);
            vemPortaSerial = portaSerial.lerDados();
            vaiParaCliente.println(vemPortaSerial);            
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            vemDoCliente.close();
            vaiParaCliente.close();
        }
    }
    
    public void run(){
        clienteSupervisorio.pausar();
        trataRequisicaoCliente();
        clienteSupervisorio.prosseguir();
    }
}
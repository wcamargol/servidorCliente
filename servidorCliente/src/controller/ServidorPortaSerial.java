/**
 * Código adaptado de http://pt.stackoverflow.com/questions/43127/multi-thread-em-socket-java
 */
package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        /**
         * Inicializa a porta serial 
         */
        portaSerial = new PortaSerial("/dev/ttyUSB0", 2000);
        try {
            /**
             * Estabelece a porta para a comunicacao com os clientes
             */
            servidor = new ServerSocket (12345);
            /**
             * Cria o cliente para o processo supervisorio
             */
            clienteSupervisorio = new ClienteSupervisorio();
            Thread threadClienteSupervisorio = new Thread(clienteSupervisorio);
            threadClienteSupervisorio.start();
            
            while (true) {
                Socket socketCliente = null;
                socketCliente = servidor.accept();
                /**
                 * Cria uma thread do servidor para tratar a conexão
                 */
                ServidorPortaSerial threadServidor = new ServidorPortaSerial(socketCliente);
                Thread thread = new Thread(threadServidor);                
                /**
                 * Inicia a thread para o cliente conectado
                 */
                thread.start(); 
            }                 
       }catch (IOException ex) {
            //portaSerial.fecharPorta();
            ex.printStackTrace();
        }
    }
    
    public void trataRequisicaoCliente(){
        /**
         * Objeto para leitura das requisicoes do cliente
         */
        Scanner vemDoCliente = null;
        /**
         * Objeto para resposta aos cliente
         */
        PrintStream vaiParaCliente = null;
        String strEntrada, strSaida = null;
        try {
            /**
             * variavel que pega a requisicao do cliente
             */
            vemDoCliente = new Scanner(this.connSocket.getInputStream()); 
            /**
             * variavel que escreve a resposta para ao cliente
             */
            vaiParaCliente = new PrintStream(this.connSocket.getOutputStream());        
            strEntrada = vemDoCliente.nextLine();
            portaSerial.escreverDados(strEntrada);
            /**
             * Le os dados da porta serial
             */            
            strSaida = portaSerial.lerDados();
            /**
             * Entrega os dados para o cliente
             */
            vaiParaCliente.println(strSaida);            
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            vemDoCliente.close();
            vaiParaCliente.close();
        }
    }
    
    public void run(){
        /**
         * pausa o cliente do processo supervisorio
         */
        clienteSupervisorio.pausar();
        /**
         * trata a requisicao do cliente
         */
        trataRequisicaoCliente();
        /**
         * reinicia o processo supervisorio
         */
        clienteSupervisorio.prosseguir();
    }
}
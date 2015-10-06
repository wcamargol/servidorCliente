/*
 * Código adaptado de http://pt.stackoverflow.com/questions/43127/multi-thread-em-socket-java
 */
package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import model.PortaSerial;

public class Server implements Runnable{
    private final Socket connSocket;
    private final PortaSerial portaSerial;

    public Server(Socket cliente){
        this.connSocket = cliente;        
        this.portaSerial = new PortaSerial("/dev/ttyUSB0",2000);
    }

    public static void main(String[] args){     

        //Cria um socket na porta 12345
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket (12345);
            System.out.println("Porta 12345 aberta!");
            // Aguarda alguém se conectar. A execução do servidor
            // fica bloqueada na chamada do método accept da classe
            // ServerSocket. Quando alguém se conectar ao servidor, o
            // método desbloqueia e retorna com um objeto da classe
            // Socket, que é uma porta da comunicação.
            while (true) {
                Socket cliente = null;
                cliente = servidor.accept();
                // Cria uma thread do servidor para tratar a conexão
                Server threadServidor = new Server(cliente);
                Thread thread = new Thread(threadServidor);
                // Inicia a thread para o cliente conectado
                thread.start(); 
            }            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void esperaMsg(){
        //Mensagem mensagem = new Mensagem();                
        Scanner entrada = null;
        PrintStream saida = null;
        try {
            entrada = new Scanner(this.connSocket.getInputStream());        
            saida = new PrintStream(this.connSocket.getOutputStream());
            this.portaSerial.escreverDados(entrada.nextLine());
            saida.println(this.portaSerial.lerDados());
            entrada.close();
            saida.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    } //fim do metodo esperaMsg

    /* A classe Thread, que foi instancia no servidor, implementa Runnable.
       Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'.
    */
    public void run(){
        esperaMsg();
        this.portaSerial.fecharPorta();        
    }
}
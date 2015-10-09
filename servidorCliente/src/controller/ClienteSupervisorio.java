package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSupervisorio implements Runnable{

    private Socket connSocket;
    private boolean pausar = false;
    
    public String enviaComando(String str){
        Scanner entrada = null;
        PrintStream saida = null;
        String respostaServidor = null;
        try {
            entrada = new Scanner(this.connSocket.getInputStream());
            saida = new PrintStream(this.connSocket.getOutputStream());
            saida.println(str);
            saida.flush();
            while (entrada.hasNextLine()) {
                respostaServidor = entrada.nextLine();
            } //fim do while
        } catch (IOException ex) {}
        finally{
           entrada.close();
           saida.close();
        }
        System.out.println(respostaServidor);
        return respostaServidor;
    }
    
    private synchronized void threadPausou(){
        while (pausar){
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }        
    }
    
    public void pausar(){
        this.pausar = true;
    }
    
    public synchronized void prosseguir(){
        this.pausar = false;
        notify();
    }

    @Override
    public void run() {
        while(true){
            threadPausou();
            try {
                this.connSocket = new Socket("127.0.0.1", 12345);
                enviaComando("?");
                Thread.sleep(1000);                
            } catch (IOException | InterruptedException ex) {
                System.exit(1);
            }finally{
                try {
                    this.connSocket.close();
                } catch (IOException ex) {}
            }
        }
    }
}
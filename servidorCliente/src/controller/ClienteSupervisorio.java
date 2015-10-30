package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import model.beans.SensorBean;
import model.dao.SensorMySQLDAO;

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
        SensorMySQLDAO sensorMySQLDAO = new SensorMySQLDAO();
        List listaSensores = sensorMySQLDAO.listSensorBean(); 
        SensorBean sensorBean = null;
        String comando = null;
        while(true){
            threadPausou();
            try {
                this.connSocket = new Socket("127.0.0.1", 12345);
                for(Object obj:listaSensores){
                    sensorBean = (SensorBean) obj; 
                    comando = "?"+sensorBean.getPinoArduino();
                    enviaComando(comando);
                }                
               Thread.sleep(10);                
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
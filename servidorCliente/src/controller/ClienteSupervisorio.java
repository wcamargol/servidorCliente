package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.beans.AtuadorBean;
import model.beans.EventoBean;
import model.beans.EventoIdBean;
import model.beans.MoradorBean;
import model.beans.SensorBean;
import model.dao.AtuadorMySQLDAO;
import model.dao.EventoMySQLDAO;
import model.dao.MoradorMySQLDAO;
import model.dao.SensorMySQLDAO;

public class ClienteSupervisorio implements Runnable{

    private Socket connSocket;
    private boolean pausar = false;
    private SensorBean sensorBean;
    private String requisicao;
    private Boolean atuadorLigado;
    
    private void trataResposta(String resposta){        
        if (resposta != null){
            if (this.sensorBean.getLimiteSupAtuacao()!= null && 
                Float.valueOf(resposta) >= this.sensorBean.getLimiteSupAtuacao()){
                geraAlarme(); 
                produzEvento();
            }else if (this.sensorBean.getLimiteInfAtuacao()!= null && 
                Float.valueOf(resposta) <= this.sensorBean.getLimiteInfAtuacao()){
                geraAlarme();
                produzEvento();
            }else{
                desligaAtuadores();
            }
        }
    }
    
    private void geraAlarme(){ 
        Runtime run = Runtime.getRuntime();
        String alarme = " "+this.sensorBean.getAlarme().getDescricaoAlarme()+
            "-"+this.sensorBean.getAmbiente().getDescricaoAmbiente();
        MoradorMySQLDAO moradorMySQLDAO = new MoradorMySQLDAO();
        List listaMoradores = moradorMySQLDAO.listMoradorBean();
        MoradorBean moradorBean = null;
        for(Object obj: listaMoradores){
            moradorBean = (MoradorBean) obj;
            try {
            run.exec("yowsup "+ moradorBean.getCelular()+alarme);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }        
        acionaAtuadores("L");
    }
    
    private void acionaAtuadores(String operacao){
        AtuadorMySQLDAO atuadorMySQLDAO = new AtuadorMySQLDAO();
        List listaAtuadores = atuadorMySQLDAO.listAtuadorBean();
        AtuadorBean atuadorBean = null;
        String comando = null;
        for(Object obj:listaAtuadores){
            atuadorBean = (AtuadorBean) obj; 
            if (atuadorBean.getSensor() != null 
                && atuadorBean.getSensor().getCodigoSensor().equals(this.sensorBean.getCodigoSensor())){
                this.requisicao = operacao + atuadorBean.getPinoArduino();
                atuadorBean.setComando(operacao.equals("L")?"D":"L");
                atuadorMySQLDAO.updateAtuadorBean(atuadorBean);
            }               
        }
    }
    
    private void produzEvento(){  
        EventoIdBean eventoIdBean = new EventoIdBean();        
        eventoIdBean.setAlarme(this.sensorBean.getAlarme());
        eventoIdBean.setSensor(this.sensorBean);
        
        EventoBean eventoBean = new EventoBean();
        eventoBean.setId(eventoIdBean);
        eventoBean.setDataEvento(new Date());
        eventoBean.setHoraEvento(new Date());
        
        EventoMySQLDAO eventoMySQLDAO = new EventoMySQLDAO();
        eventoMySQLDAO.saveEventoBean(eventoBean);
    }
    
    private void desligaAtuadores(){
        acionaAtuadores("D");
    }
    
    private String enviaRequisicao(){        
        Scanner entrada = null;
        PrintStream saida = null;
        String respostaServidor = null;
        try {
            entrada = new Scanner(this.connSocket.getInputStream());
            saida = new PrintStream(this.connSocket.getOutputStream());
            saida.println(this.requisicao);
            saida.flush();
            while (entrada.hasNextLine()) {
                respostaServidor = entrada.nextLine();
            }
        } catch (IOException ex) {
            entrada.close();
            saida.close();
           ex.printStackTrace();            
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
        String requisicao = null,resposta;
        while(true){
            threadPausou();
            resposta = null;
            this.sensorBean = null;
            try {
                this.connSocket = new Socket("127.0.0.1", 12345);
                for(Object obj:listaSensores){
                    this.sensorBean = (SensorBean) obj;
                    if (this.requisicao == null){
                        this.requisicao = "?"+this.sensorBean.getPinoArduino();
                    }
                    resposta = enviaRequisicao();
                    Thread.sleep(10);
                    if (resposta != null && 
                        (!resposta.equals("L") && !resposta.equals("D"))){
                        trataResposta(resposta);                        
                    }else{
                        this.requisicao = null;
                    }                   
                }                
            }catch (IOException | InterruptedException ex) {
                ex.printStackTrace();                
                System.exit(1);
            }           
        }
    }
}
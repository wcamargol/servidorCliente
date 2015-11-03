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
    
    private void trataResposta(String reposta, SensorBean sensor){
        if (sensor.getLimiteInfAlarme()!= null && 
            Float.valueOf(reposta) >= sensor.getLimiteSupAlarme()){
            geraAlarme(sensor); 
            produzEvento(sensor);
        }else if (sensor.getLimiteInfAlarme()!= null && 
            Float.valueOf(reposta) <= sensor.getLimiteInfAlarme()){
            geraAlarme(sensor);
            produzEvento(sensor);
        }else{
            desligaAtuadores(sensor);
        }
    }
    
    private void geraAlarme(SensorBean sensor){
        Runtime run = Runtime.getRuntime();
        String alarme = " "+sensor.getAlarme().getDescricaoAlarme()+
            "-"+sensor.getAmbiente().getDescricaoAmbiente();
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
        acionaAtuadores(sensor, "L");
    }
    
    private void acionaAtuadores(SensorBean sensor, String operacao){
        AtuadorMySQLDAO atuadorMySQLDAO = new AtuadorMySQLDAO();
        List listaAtuadores = atuadorMySQLDAO.listAtuadorBean();
        AtuadorBean atuadorBean = null;
        for(Object obj:listaAtuadores){
            atuadorBean = (AtuadorBean) obj;
            if (atuadorBean.getSensor() != null 
                && atuadorBean.getSensor().getCodigoSensor().equals(sensor.getCodigoSensor())
                && operacao.equals(atuadorBean.getComando())){
                enviaRequisicao(atuadorBean.getAmbiente().getCodigoAmbiente()+
                    atuadorBean.getCodigoAtuador()+
                    atuadorBean.getComando()+
                    atuadorBean.getPinoArduino());
                atuadorBean.setComando(operacao.equals("L")?"D":"L");
                atuadorMySQLDAO.updateAtuadorBean(atuadorBean);
            }               
        }        
    }
    
    private void produzEvento(SensorBean sensor){        
        
        EventoIdBean eventoIdBean = new EventoIdBean();
        eventoIdBean.setAlarme(sensor.getAlarme());
        eventoIdBean.setSensor(sensor);
        
        EventoBean eventoBean = new EventoBean();
        eventoBean.setId(eventoIdBean);
        eventoBean.setDataEvento(new Date());
        eventoBean.setHoraEvento(new Date());
        
        EventoMySQLDAO eventoMySQLDAO = new EventoMySQLDAO();
        eventoMySQLDAO.saveEventoBean(eventoBean);
    }
    
    private void desligaAtuadores(SensorBean sensor){
        acionaAtuadores(sensor, "D");
    }
    
    public String enviaRequisicao(String str){
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
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        String requisicao = null, 
            resposta = null;
        while(true){
            threadPausou();
            try {
                this.connSocket = new Socket("127.0.0.1", 12345);
                for(Object obj:listaSensores){
                    sensorBean = (SensorBean) obj; 
                    requisicao = "?"+sensorBean.getPinoArduino();
                    resposta = enviaRequisicao(requisicao);
                    Thread.sleep(10);
                    if (resposta != null && 
                        (!resposta.equals("L") || !resposta.equals("D"))){
                        trataResposta(resposta, sensorBean);
                    }
                }              
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
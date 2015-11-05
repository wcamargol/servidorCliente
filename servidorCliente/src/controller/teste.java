/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.List;
import model.beans.AtuadorBean;
import model.beans.SensorBean;
import model.dao.AtuadorMySQLDAO;
import model.dao.SensorMySQLDAO;

/**
 *
 * @author lubuntu
 */
public class teste {
    private static SensorBean sensorBean;
    public static void main(String[] args) throws IOException{
        AtuadorMySQLDAO equipamentoMySQLDAO = new AtuadorMySQLDAO();
        List listaAtuadoresBean = equipamentoMySQLDAO.listAtuadorBean();
        for(Object obj : listaAtuadoresBean){
                AtuadorBean e = (AtuadorBean) obj;
                System.out.println(e.getDescricaoAtuador());
        }
        System.exit(0);
    }    
}

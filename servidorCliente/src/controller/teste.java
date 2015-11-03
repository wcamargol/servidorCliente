/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import model.beans.AlarmeBean;
import model.beans.AtuadorBean;
import model.beans.MoradorBean;
import model.beans.SensorBean;
import model.dao.AtuadorMySQLDAO;
import model.dao.MoradorMySQLDAO;
import model.dao.SensorMySQLDAO;

/**
 *
 * @author lubuntu
 */
public class teste {
    public static void main(String[] args){
        AtuadorMySQLDAO atuadorMySQLDAO = new AtuadorMySQLDAO();
        List listaAtuadores = atuadorMySQLDAO.listAtuadorBean();
        AtuadorBean atuadorBean = null;
        for(Object obj:listaAtuadores){
            atuadorBean = (AtuadorBean) obj;
            
            System.out.println(atuadorBean.getRequerLogin());                
        }
        System.exit(0);
    }    
}

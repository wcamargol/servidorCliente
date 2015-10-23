/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.List;
import model.beans.MoradorBean;
import model.dao.MoradorMySQLDAO;

/**
 *
 * @author lubuntu
 */
public class teste {
    public static void main(String[] args){
        Runtime run = Runtime.getRuntime();
        String msg = " isso é um teste de novo";
        MoradorMySQLDAO moradorMySQLDAO = new MoradorMySQLDAO();
        List listaMoradores = moradorMySQLDAO.listMoradorBean();
        MoradorBean moradorBean = null;
        for(Object obj: listaMoradores){
            moradorBean = (MoradorBean) obj;
            try {
            run.exec("yowsup "+ moradorBean.getCelular()+msg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.exit(0);
    }    
}

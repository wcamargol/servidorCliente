package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;


public class Mensagem implements Serializable {
	
    private static final long serialVersionUID = 1L;    
    private String codigo;
    private String mensagem;
    
    public Mensagem(){
        codigo = null;
        mensagem = null;
    }
    
    public Mensagem(String cod, String msg){
        codigo = cod;
        mensagem = msg;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setMsg(String str){
            mensagem = str;
    }

    public String getMsg(){
            return mensagem;
    }
}

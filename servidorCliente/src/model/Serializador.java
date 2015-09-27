package model;


import model.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lubuntu
 */
public class Serializador {
    
    public byte[] serialize(Mensagem msg) throws IOException {
        Object obj = (Object) msg;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        //retorna um array de bytes
        return bos.toByteArray();
    }//fim do metodo serialize
   
	 //Desserializa o array de bytes para o objeto Mensagem 
    public Mensagem deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Mensagem msg = (Mensagem)ois.readObject();
        //retorna um objeto mensagem
        return msg;
    }// fim do metodo deserialize	
    
}

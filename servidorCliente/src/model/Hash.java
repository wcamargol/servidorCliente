package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Hash {
    public String getMD5(String senha) throws NoSuchAlgorithmException{
        MessageDigest algoritmo;
        algoritmo = MessageDigest.getInstance("MD5");
        algoritmo.reset();
        algoritmo.update(senha.getBytes());
        byte [] messageDigest = algoritmo.digest();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < messageDigest.length; i++) {
            int parteAlta = ((messageDigest[i] >> 4) & 0xf) << 4; 
            int parteBaixa = messageDigest[i] & 0xf;
            if (parteAlta == 0)
                s.append('0');
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
    
}

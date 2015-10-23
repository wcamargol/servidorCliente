package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {    
    public String getMD5(String senha) throws NoSuchAlgorithmException{
      MessageDigest algoritmo; //Cria objeto usado para obter instância do algoritmo de criptografia MD5
      algoritmo = MessageDigest.getInstance("MD5"); //define o algoritmo a ser utilizado
      algoritmo.reset(); //por padrão chama reset antes de utilizar
      algoritmo.update(senha.getBytes()); //Fornece os bytes referentes à senha para gerar o hash
      byte [] messageDigest = algoritmo.digest(); //Obtém o hash
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < messageDigest.length; i++) //Compatibiliza o hash do Java com o do MySQL
      {
        int parteAlta = ((messageDigest[i] >> 4) & 0xf) << 4; //Faz deslocamento de bits e AND lógico
        int parteBaixa = messageDigest[i] & 0xf; //faz AND lógico com o hash e 0xF
        if (parteAlta == 0) //Se parte alta for 0, concatena um 0 na string que representa o hash
          s.append('0');
        s.append(Integer.toHexString(parteAlta | parteBaixa)); //faz OR lógico entre a parte alta e parte baixa
      }
      return s.toString(); //Retorna a string que representa o hash da senha
   }    
}

package controller;


import model.PortaSerial;
import view.SupervisorioSSHouse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Servidor {
    
    //executa o programa servidor
    public static void main( String args[] ) throws InterruptedException{
        PortaSerial portaSerial = new PortaSerial("/dev/ttyUSB0",2000);        
        ServidorThread sT = new ServidorThread(portaSerial);
        Thread threadST = new Thread(sT);
        threadST.start();
        SupervisorioSSHouse sSH = new SupervisorioSSHouse(portaSerial);
        sSH.setVisible(true);
        Thread threadSSH = new Thread(sSH);
        threadSSH.start();
    } //fim de main*/

    
} //fim da classe Servidor

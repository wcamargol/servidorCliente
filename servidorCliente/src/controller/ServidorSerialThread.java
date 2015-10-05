package controller;


import model.PortaSerial;


/*
 *  
 */
public class ServidorSerialThread implements Runnable {
    
    private final PortaSerial portaSerial;
    private String mensagem;
    private boolean parar;
    
   ServidorSerialThread(PortaSerial portaSerial){
        this.mensagem = null;
        this.portaSerial = portaSerial;
        this.parar = false;
        /*Thread t = new Thread(new ServidorSerialThreadRun());
        t.setDaemon(true);
        t.start();*/
    }
    
    public void escutarPortaSerial(){
        while(true){            
            this.mensagem = this.portaSerial.lerDados();
        }
    }
    
    private synchronized void pararThread(){
        while (this.parar) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }
    }
    
    public synchronized void pausarThread(boolean parar) {
        this.parar = parar;
        if (!parar){
             notify();
         }
    }

    @Override
    public void run() {
        escutarPortaSerial();
    }

    /*@Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class ServidorSerialThreadRun implements Runnable {
        public void run() {
            try {
                while (true) {                                
                    pararThread();
                    mensagem = portaSerial.lerDados();
                    System.out.println(mensagem);
                    //escutarPortaSerial();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {}
        }
    }*/
}

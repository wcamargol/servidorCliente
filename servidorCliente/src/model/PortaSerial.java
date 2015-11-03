package model;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class PortaSerial implements SerialPortEventListener {
    private CommPortIdentifier portaId;
    private SerialPort portaSerial;
    private BufferedReader entrada;
    private OutputStream saida;
    private String dadosRecebidos;
    public static final int DATA_RATE = 9600;
    
    
    public PortaSerial(String porta, int timeOut){
        try {
            this.portaId= CommPortIdentifier.getPortIdentifier(porta);
            this.portaSerial = (SerialPort)portaId.open(porta, timeOut);
            this.portaSerial.setSerialPortParams(DATA_RATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
            this.portaSerial.addEventListener(this);
            this.portaSerial.notifyOnDataAvailable(true);
        } catch (NoSuchPortException ex) {
            System.out.println("Porta não encontrada.");
        } catch (PortInUseException ex) {
            System.out.println("Não foi possivel abrir a porta.");
        } catch (UnsupportedCommOperationException | TooManyListenersException ex) {
            this.portaSerial.close();
            ex.printStackTrace();
        }
    }       
    
    public String lerDados(){
        return this.dadosRecebidos;
    }
    
    public void escreverDados(String dados){
        String str;
        try {            
            this.saida = portaSerial.getOutputStream();
                this.saida.write(dados.getBytes());                
                Thread.sleep(100);    
        } catch (IOException | InterruptedException ex) {
            System.out.println("Problemas na execução do comando.");
        }
    }
    
    public SerialPort getSerialPort(){
        return this.portaSerial;
    }
    
    public synchronized void fecharPorta() {
        if (this.portaSerial != null) {
            this.portaSerial.removeEventListener();
            this.portaSerial.close();
        }
    }
    
    @Override
    public synchronized void serialEvent(SerialPortEvent spe) {
        try{ 
            switch (spe.getEventType() ) {
                case SerialPortEvent.DATA_AVAILABLE: 
                    if (this.entrada == null) {
                        this.entrada = new BufferedReader(
                            new InputStreamReader(
                                    this.portaSerial.getInputStream()));
                    }
                    if (this.entrada.ready()){
                        this.dadosRecebidos = this.entrada.readLine();
                        break;
                    }
                default:
                    break;
            }
        }catch (Exception ex) {
            System.out.println("Problemas na leitura dos dados.");
        }
    }
}

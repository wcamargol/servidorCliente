package model.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Atuador")
public class AtuadorBean  implements Serializable {
    
    @Id
    private String codigoAtuador;
    private String comando;
    private String pinoArduino;
    private String descricaoAtuador;
    private Boolean requerLogin;
    
    @ManyToOne
    @JoinColumn(name="codigoAmbiente")
    private AmbienteBean ambiente;
    
    @ManyToOne
    @JoinColumn(name="codigoSensor")
    private SensorBean sensor;
    
    @ManyToMany
    @JoinTable(name="Operacao", schema="SSHouse",
        joinColumns=@JoinColumn(name="codigoAtuador"),
        inverseJoinColumns=@JoinColumn(name="login"))    
    private Collection<MoradorBean> moradores; 

    public String getCodigoAtuador() {
        return codigoAtuador;
    }

    public void setCodigoAtuador(String codigoAtuador) {
        this.codigoAtuador = codigoAtuador;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getPinoArduino() {
        return pinoArduino;
    }

    public void setPinoArduino(String pinoArduino) {
        this.pinoArduino = pinoArduino;
    }

    public String getDescricaoAtuador() {
        return descricaoAtuador;
    }

    public void setDescricaoAtuador(String descricaoAtuador) {
        this.descricaoAtuador = descricaoAtuador;
    }

    public Boolean getRequerLogin() {
        return requerLogin;
    }

    public void setRequerLogin(Boolean requerLogin) {
        this.requerLogin = requerLogin;
    }

    public AmbienteBean getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(AmbienteBean ambiente) {
        this.ambiente = ambiente;
    }

    public SensorBean getSensor() {
        return sensor;
    }

    public void setSensor(SensorBean sensor) {
        this.sensor = sensor;
    }

    public Collection<MoradorBean> getMoradores() {
        return moradores;
    }

    public void setMoradores(Collection<MoradorBean> moradores) {
        this.moradores = moradores;
    }
}
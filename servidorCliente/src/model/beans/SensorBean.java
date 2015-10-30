package model.beans;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sensor")
public class SensorBean  implements Serializable {
    
    @Id
    private String codigoSensor;
    private String descricaoSensor;
    private String pinoArduino;
    private String limiteInfAlarme;
    private String limiteSupAlarme;
    private String limiteInfAtuacao;
    private String limiteSupAtuacao;    
    
    @ManyToOne
    @JoinColumn(name="codigoAmbiente")
    private AmbienteBean ambiente;
    
    @OneToMany(mappedBy="sensor",fetch=FetchType.LAZY)
    private Collection<AtuadorBean> atuador;
    
    @ManyToMany
    @JoinTable(name="Evento", schema="SSHouse",
        joinColumns=@JoinColumn(name="codigoSensor"),
        inverseJoinColumns=@JoinColumn(name="codigoAlarme"))
    private Collection<AlarmeBean> alarme;

    public String getCodigoSensor() {
        return codigoSensor;
    }

    public void setCodigoSensor(String codigoSensor) {
        this.codigoSensor = codigoSensor;
    }

    public String getDescricaoSensor() {
        return descricaoSensor;
    }

    public void setDescricaoSensor(String descricaoSensor) {
        this.descricaoSensor = descricaoSensor;
    }

    public String getPinoArduino() {
        return pinoArduino;
    }

    public void setPinoArduino(String pinoArduino) {
        this.pinoArduino = pinoArduino;
    }

    public String getLimiteInfAlarme() {
        return limiteInfAlarme;
    }

    public void setLimiteInfAlarme(String limiteInfAlarme) {
        this.limiteInfAlarme = limiteInfAlarme;
    }

    public String getLimiteSupAlarme() {
        return limiteSupAlarme;
    }

    public void setLimiteSupAlarme(String limiteSupAlarme) {
        this.limiteSupAlarme = limiteSupAlarme;
    }

    public String getLimiteInfAtuacao() {
        return limiteInfAtuacao;
    }

    public void setLimiteInfAtuacao(String limiteInfAtuacao) {
        this.limiteInfAtuacao = limiteInfAtuacao;
    }

    public String getLimiteSupAtuacao() {
        return limiteSupAtuacao;
    }

    public void setLimiteSupAtuacao(String limiteSupAtuacao) {
        this.limiteSupAtuacao = limiteSupAtuacao;
    }

    public AmbienteBean getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(AmbienteBean ambiente) {
        this.ambiente = ambiente;
    }

    public Collection<AlarmeBean> getAlarme() {
        return alarme;
    }

    public void setAlarme(Collection<AlarmeBean> alarme) {
        this.alarme = alarme;
    }    

    public Collection<AtuadorBean> getAtuador() {
        return atuador;
    }

    public void setAtuador(Collection<AtuadorBean> atuador) {
        this.atuador = atuador;
    }
}
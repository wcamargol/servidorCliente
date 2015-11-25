package model.beans;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    private Float limiteInfAtuacao;
    private Float limiteSupAtuacao;   
    
    @ManyToOne
    @JoinColumn(name="codigoAmbiente")
    private AmbienteBean ambiente;
    
    @ManyToOne
    @JoinColumn(name="codigoAlarme")
    private AlarmeBean alarme;
    
    @OneToMany(mappedBy="sensor",fetch=FetchType.LAZY)
    private Collection<AtuadorBean> atuador;
    
    /*@ManyToMany
    @JoinTable(name="Evento", schema="SSHouse",
        joinColumns=@JoinColumn(name="codigoSensor"),
        inverseJoinColumns=@JoinColumn(name="codigoAtuacao"))*/
    @OneToMany(mappedBy="sensor",fetch=FetchType.LAZY)
    private Collection<EventoBean> eventos;

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

    public Float getLimiteInfAtuacao() {
        return limiteInfAtuacao;
    }

    public void setLimiteInfAtuacao(Float limiteInfAtuacao) {
        this.limiteInfAtuacao = limiteInfAtuacao;
    }

    public Float getLimiteSupAtuacao() {
        return limiteSupAtuacao;
    }

    public void setLimiteSupAtuacao(Float limiteSupAtuacao) {
        this.limiteSupAtuacao = limiteSupAtuacao;
    }

    public AmbienteBean getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(AmbienteBean ambiente) {
        this.ambiente = ambiente;
    }
    
    public AlarmeBean getAlarme() {
        return alarme;
    }

    public void setAlarme(AlarmeBean alarme) {
        this.alarme = alarme;
    }

    public Collection<AtuadorBean> getAtuador() {
        return atuador;
    }

    public void setAtuador(Collection<AtuadorBean> atuador) {
        this.atuador = atuador;
    }
    
    public Collection<EventoBean> getEventos() {
        return eventos;
    }

    public void setEventos(Collection<EventoBean> eventos) {
        this.eventos = eventos;
    }
}
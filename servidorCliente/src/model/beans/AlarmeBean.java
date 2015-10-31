package model.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Alarme")
public class AlarmeBean  implements Serializable {
    
    @Id
    @Column(name = "codigoAlarme")
    private String codigoAlarme;
    private String descricaoAlarme;
    
    @OneToMany(mappedBy="alarme",fetch=FetchType.LAZY)
    private Collection<SensorBean> sensorAlarme;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="Evento", schema="SSHouse",
        joinColumns=@JoinColumn(name="codigoAlarme"),
        inverseJoinColumns=@JoinColumn(name="codigoSensor"))    
    private Collection<SensorBean> sensorEvento;

    public String getCodigoAlarme() {
        return codigoAlarme;
    }

    public void setCodigoAlarme(String codigoAlarme) {
        this.codigoAlarme = codigoAlarme;
    }

    public String getDescricaoAlarme() {
        return descricaoAlarme;
    }

    public void setDescricaoAlarme(String descricaoAlarme) {
        this.descricaoAlarme = descricaoAlarme;
    }

    public Collection<SensorBean> getSensorAlarme() {
        return sensorAlarme;
    }

    public void setSensorAlarme(Collection<SensorBean> sensorAlarme) {
        this.sensorAlarme = sensorAlarme;
    }

    public Collection<SensorBean> getSensorEvento() {
        return sensorEvento;
    }

    public void setSensorEvento(Collection<SensorBean> sensorEvento) {
        this.sensorEvento = sensorEvento;
    }
}



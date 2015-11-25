package model.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
    private Collection<SensorBean> sensor;
    
    @OneToMany(mappedBy="alarme",fetch=FetchType.LAZY)
    private Collection<EventoBean> eventos;

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

    public Collection<SensorBean> getSensor() {
        return sensor;
    }

    public void setSensor(Collection<SensorBean> sensor) {
        this.sensor = sensor;
    }

    public Collection<EventoBean> getEventos() {
        return eventos;
    }

    public void setSensorEvento(Collection<EventoBean> eventos) {
        this.eventos = eventos;
    }
}
package model.beans;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EventoIdBean  implements java.io.Serializable {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoSensor")
    private SensorBean sensor;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoAlarme")
    private AlarmeBean alarme;
    
    public SensorBean getSensor() {
        return sensor;
    }

    public void setSensor(SensorBean sensor) {
        this.sensor = sensor;
    }

    public AlarmeBean getAlarme() {
        return alarme;
    }

    public void setAlarme(AlarmeBean alarme) {
        this.alarme = alarme;
    }
}
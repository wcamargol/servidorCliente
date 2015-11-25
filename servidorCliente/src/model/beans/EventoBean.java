package model.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Evento")
public class EventoBean  implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "codigoSensor")
    private SensorBean sensor;
    //private EventoIdBean id;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "codigoAlarme")
    private AlarmeBean alarme;
    
    @Id
    @Temporal(TemporalType.DATE)
    private Date dataEvento;
    
    @Id
    @Temporal(TemporalType.TIME)
    private Date horaEvento;

    /*public EventoIdBean getId() {
        return id;
    }

    public void setId(EventoIdBean id) {
        this.id = id;
    }*/
    
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

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        formatadorData.setLenient(false);
        formatadorData.format(dataEvento);
        this.dataEvento = dataEvento;
    }

    public Date getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(Date horaEvento) {
        SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
        formatadorHora.setLenient(false);
        formatadorHora.format(horaEvento);
        this.horaEvento = horaEvento;
    }
}
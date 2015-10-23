package model.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Evento")
public class EventoBean  implements Serializable {

    @EmbeddedId
    private EventoIdBean id;
    
    @Id
    @Temporal(TemporalType.DATE)
    private Date dataEvento;
    
    @Id
    @Temporal(TemporalType.TIME)
    private Date horaEvento;

    public EventoIdBean getId() {
        return id;
    }

    public void setId(EventoIdBean id) {
        this.id = id;
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
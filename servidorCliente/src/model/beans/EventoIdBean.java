package model.beans;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EventoIdBean  implements java.io.Serializable {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoEquipamento")
    private EquipamentoBean equipamento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoAlarme")
    private AlarmeBean alarme;
    
    public EquipamentoBean getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(EquipamentoBean equipamento) {
        this.equipamento = equipamento;
    }

    public AlarmeBean getAlarme() {
        return alarme;
    }

    public void setAlarme(AlarmeBean alarme) {
        this.alarme = alarme;
    }
}
package model.beans;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OperacaoIdBean  implements java.io.Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoEquipamento")
    private EquipamentoBean equipamento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login")
    private MoradorBean morador;
    
    public EquipamentoBean getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(EquipamentoBean equipamento) {
        this.equipamento = equipamento;
    }

    public MoradorBean getMorador() {
        return morador;
    }

    public void setMorador(MoradorBean morador) {
        this.morador = morador;
    }
}
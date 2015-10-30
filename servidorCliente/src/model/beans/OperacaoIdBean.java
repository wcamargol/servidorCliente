package model.beans;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OperacaoIdBean  implements java.io.Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoAtuador")
    private AtuadorBean atuador;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login")
    private MoradorBean morador;
    
    public AtuadorBean getAtuador() {
        return atuador;
    }

    public void setAtuador(AtuadorBean atuador) {
        this.atuador = atuador;
    }

    public MoradorBean getMorador() {
        return morador;
    }

    public void setMorador(MoradorBean morador) {
        this.morador = morador;
    }
}
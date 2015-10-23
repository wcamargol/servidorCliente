package model.beans;

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
@Table(name = "Operacao")
public class OperacaoBean  implements java.io.Serializable {
    
    @EmbeddedId
    private OperacaoIdBean id;
    @Id
    @Temporal(TemporalType.DATE)
    private Date dataOperacao;
    @Id
    @Temporal(TemporalType.TIME)
    private Date horaOperacao;
    
    private String descricaoOperacao;

    public OperacaoIdBean getId() {
        return id;
    }

    public void setId(OperacaoIdBean id) {
        this.id = id;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(Date dataOperacao) {
        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        formatadorData.setLenient(false);
        formatadorData.format(dataOperacao);
        this.dataOperacao = dataOperacao;
    }

    public Date getHoraOperacao() {
        return horaOperacao;
    }

    public void setHoraOperacao(Date horaOperacao) {
        SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
        formatadorHora.setLenient(false);
        formatadorHora.format(horaOperacao);
        this.horaOperacao = horaOperacao;
    }
    
    public String getDescricaoOperacao(){
        return this.descricaoOperacao;
    }
    
    public void setDescricaoOperacao(String descricaoOperacao){
        this.descricaoOperacao = descricaoOperacao;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "Ambiente")
public class AmbienteBean  implements Serializable {
    
    @Id
    @Column(name = "codigoAmbiente")
    private String codigoAmbiente;
    private String descricaoAmbiente;
    
    @OneToMany(mappedBy="ambiente",fetch=FetchType.LAZY)
    private Collection<SensorBean> sensor;
    
    @OneToMany(mappedBy="ambiente",fetch=FetchType.LAZY)
    private Collection<AtuadorBean> atuador;

    public String getCodigoAmbiente() {
        return codigoAmbiente;
    }

    public void setCodigoAmbiente(String codigoAmbiente) {
        this.codigoAmbiente = codigoAmbiente;
    }

    public String getDescricaoAmbiente() {
        return descricaoAmbiente;
    }

    public void setDescricaoAmbiente(String descricaoAmbiente) {
        this.descricaoAmbiente = descricaoAmbiente;
    }

    public Collection<SensorBean> getSensor() {
        return sensor;
    }

    public void setSensor(Collection<SensorBean> sensor) {
        this.sensor = sensor;
    }    

    public Collection<AtuadorBean> getAtuador() {
        return atuador;
    }

    public void setAtuador(Collection<AtuadorBean> atuador) {
        this.atuador = atuador;
    }
}

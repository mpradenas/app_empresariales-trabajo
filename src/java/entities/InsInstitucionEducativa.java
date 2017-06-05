/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gamalyon
 */
@Entity
@Table(name = "ins_institucion_educativa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InsInstitucionEducativa.findAll", query = "SELECT i FROM InsInstitucionEducativa i")
    , @NamedQuery(name = "InsInstitucionEducativa.findByIntsIdinstitucion", query = "SELECT i FROM InsInstitucionEducativa i WHERE i.intsIdinstitucion = :intsIdinstitucion")
    , @NamedQuery(name = "InsInstitucionEducativa.findByInstNombre", query = "SELECT i FROM InsInstitucionEducativa i WHERE i.instNombre = :instNombre")
    , @NamedQuery(name = "InsInstitucionEducativa.findByInstFechainicio", query = "SELECT i FROM InsInstitucionEducativa i WHERE i.instFechainicio = :instFechainicio")
    , @NamedQuery(name = "InsInstitucionEducativa.findByInstFechatermino", query = "SELECT i FROM InsInstitucionEducativa i WHERE i.instFechatermino = :instFechatermino")
    , @NamedQuery(name = "InsInstitucionEducativa.findByVacantes", query = "SELECT i FROM InsInstitucionEducativa i WHERE i.vacantes = :vacantes")})
public class InsInstitucionEducativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ints_idinstitucion")
    private Integer intsIdinstitucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "inst_nombre")
    private String instNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inst_fechainicio")
    @Temporal(TemporalType.DATE)
    private Date instFechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inst_fechatermino")
    @Temporal(TemporalType.DATE)
    private Date instFechatermino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vacantes")
    private int vacantes;
    @OneToMany(mappedBy = "estIdinstitucion")
    private Collection<InsEstudiantes> insEstudiantesCollection;

    public InsInstitucionEducativa() {
    }

    public InsInstitucionEducativa(Integer intsIdinstitucion) {
        this.intsIdinstitucion = intsIdinstitucion;
    }

    public InsInstitucionEducativa(Integer intsIdinstitucion, String instNombre, Date instFechainicio, Date instFechatermino, int vacantes) {
        this.intsIdinstitucion = intsIdinstitucion;
        this.instNombre = instNombre;
        this.instFechainicio = instFechainicio;
        this.instFechatermino = instFechatermino;
        this.vacantes = vacantes;
    }

    public Integer getIntsIdinstitucion() {
        return intsIdinstitucion;
    }

    public void setIntsIdinstitucion(Integer intsIdinstitucion) {
        this.intsIdinstitucion = intsIdinstitucion;
    }

    public String getInstNombre() {
        return instNombre;
    }

    public void setInstNombre(String instNombre) {
        this.instNombre = instNombre;
    }

    public Date getInstFechainicio() {
        return instFechainicio;
    }

    public void setInstFechainicio(Date instFechainicio) {
        this.instFechainicio = instFechainicio;
    }

    public Date getInstFechatermino() {
        return instFechatermino;
    }

    public void setInstFechatermino(Date instFechatermino) {
        this.instFechatermino = instFechatermino;
    }

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    @XmlTransient
    public Collection<InsEstudiantes> getInsEstudiantesCollection() {
        return insEstudiantesCollection;
    }

    public void setInsEstudiantesCollection(Collection<InsEstudiantes> insEstudiantesCollection) {
        this.insEstudiantesCollection = insEstudiantesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intsIdinstitucion != null ? intsIdinstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsInstitucionEducativa)) {
            return false;
        }
        InsInstitucionEducativa other = (InsInstitucionEducativa) object;
        if ((this.intsIdinstitucion == null && other.intsIdinstitucion != null) || (this.intsIdinstitucion != null && !this.intsIdinstitucion.equals(other.intsIdinstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.InsInstitucionEducativa[ intsIdinstitucion=" + intsIdinstitucion + " ]";
    }
    
}

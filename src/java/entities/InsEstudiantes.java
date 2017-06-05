/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gamalyon
 */
@Entity
@Table(name = "ins_estudiantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InsEstudiantes.findAll", query = "SELECT i FROM InsEstudiantes i")
    , @NamedQuery(name = "InsEstudiantes.findByEstIdestudiante", query = "SELECT i FROM InsEstudiantes i WHERE i.estIdestudiante = :estIdestudiante")
    , @NamedQuery(name = "InsEstudiantes.findByEstNombre", query = "SELECT i FROM InsEstudiantes i WHERE i.estNombre = :estNombre")
    , @NamedQuery(name = "InsEstudiantes.findByEstApellidopaterno", query = "SELECT i FROM InsEstudiantes i WHERE i.estApellidopaterno = :estApellidopaterno")
    , @NamedQuery(name = "InsEstudiantes.findByEstApellidomaterno", query = "SELECT i FROM InsEstudiantes i WHERE i.estApellidomaterno = :estApellidomaterno")
    , @NamedQuery(name = "InsEstudiantes.findByEstEdad", query = "SELECT i FROM InsEstudiantes i WHERE i.estEdad = :estEdad")})
public class InsEstudiantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "est_idestudiante")
    private Integer estIdestudiante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "est_nombre")
    private String estNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "est_apellidopaterno")
    private String estApellidopaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "est_apellidomaterno")
    private String estApellidomaterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "est_edad")
    private int estEdad;
    @JoinColumn(name = "est_idinstitucion", referencedColumnName = "ints_idinstitucion")
    @ManyToOne
    private InsInstitucionEducativa estIdinstitucion;

    public InsEstudiantes() {
    }

    public InsEstudiantes(Integer estIdestudiante) {
        this.estIdestudiante = estIdestudiante;
    }

    public InsEstudiantes(Integer estIdestudiante, String estNombre, String estApellidopaterno, String estApellidomaterno, int estEdad) {
        this.estIdestudiante = estIdestudiante;
        this.estNombre = estNombre;
        this.estApellidopaterno = estApellidopaterno;
        this.estApellidomaterno = estApellidomaterno;
        this.estEdad = estEdad;
    }

    public Integer getEstIdestudiante() {
        return estIdestudiante;
    }

    public void setEstIdestudiante(Integer estIdestudiante) {
        this.estIdestudiante = estIdestudiante;
    }

    public String getEstNombre() {
        return estNombre;
    }

    public void setEstNombre(String estNombre) {
        this.estNombre = estNombre;
    }

    public String getEstApellidopaterno() {
        return estApellidopaterno;
    }

    public void setEstApellidopaterno(String estApellidopaterno) {
        this.estApellidopaterno = estApellidopaterno;
    }

    public String getEstApellidomaterno() {
        return estApellidomaterno;
    }

    public void setEstApellidomaterno(String estApellidomaterno) {
        this.estApellidomaterno = estApellidomaterno;
    }

    public int getEstEdad() {
        return estEdad;
    }

    public void setEstEdad(int estEdad) {
        this.estEdad = estEdad;
    }

    public InsInstitucionEducativa getEstIdinstitucion() {
        return estIdinstitucion;
    }

    public void setEstIdinstitucion(InsInstitucionEducativa estIdinstitucion) {
        this.estIdinstitucion = estIdinstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estIdestudiante != null ? estIdestudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsEstudiantes)) {
            return false;
        }
        InsEstudiantes other = (InsEstudiantes) object;
        if ((this.estIdestudiante == null && other.estIdestudiante != null) || (this.estIdestudiante != null && !this.estIdestudiante.equals(other.estIdestudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.InsEstudiantes[ estIdestudiante=" + estIdestudiante + " ]";
    }
    
}

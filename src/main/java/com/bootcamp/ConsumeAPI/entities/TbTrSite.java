/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Insane
 */
@Entity
@Table(name = "tb_tr_site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrSite.findAll", query = "SELECT t FROM TbTrSite t")
    , @NamedQuery(name = "TbTrSite.findById", query = "SELECT t FROM TbTrSite t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrSite.findByName", query = "SELECT t FROM TbTrSite t WHERE t.name = :name")
    , @NamedQuery(name = "TbTrSite.findByAddress", query = "SELECT t FROM TbTrSite t WHERE t.address = :address")})
public class TbTrSite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "id")
    private String id;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "address")
    private String address;
    @JoinColumn(name = "pic", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TbMEmployee pic;
    @OneToMany(mappedBy = "site", fetch = FetchType.LAZY)
    private List<TbMEmployee> tbMEmployeeList;

    public TbTrSite() {
    }

    public TbTrSite(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TbMEmployee getPic() {
        return pic;
    }

    public void setPic(TbMEmployee pic) {
        this.pic = pic;
    }

    @XmlTransient
    public List<TbMEmployee> getTbMEmployeeList() {
        return tbMEmployeeList;
    }

    public void setTbMEmployeeList(List<TbMEmployee> tbMEmployeeList) {
        this.tbMEmployeeList = tbMEmployeeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTrSite)) {
            return false;
        }
        TbTrSite other = (TbTrSite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbTrSite[ id=" + id + " ]";
    }
    
}

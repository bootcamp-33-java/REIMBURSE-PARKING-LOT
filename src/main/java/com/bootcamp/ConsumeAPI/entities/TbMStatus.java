/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "tb_m_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMStatus.findAll", query = "SELECT t FROM TbMStatus t")
    , @NamedQuery(name = "TbMStatus.findById", query = "SELECT t FROM TbMStatus t WHERE t.id = :id")
    , @NamedQuery(name = "TbMStatus.findByName", query = "SELECT t FROM TbMStatus t WHERE t.name = :name")})
public class TbMStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<TbTrHistory> tbTrHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currentStatus", fetch = FetchType.LAZY)
    private List<TbTrReimburse> tbTrReimburseList;

    public TbMStatus() {
    }

    public TbMStatus(Integer id) {
        this.id = id;
    }

    public TbMStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<TbTrHistory> getTbTrHistoryList() {
        return tbTrHistoryList;
    }

    public void setTbTrHistoryList(List<TbTrHistory> tbTrHistoryList) {
        this.tbTrHistoryList = tbTrHistoryList;
    }

    @XmlTransient
    public List<TbTrReimburse> getTbTrReimburseList() {
        return tbTrReimburseList;
    }

    public void setTbTrReimburseList(List<TbTrReimburse> tbTrReimburseList) {
        this.tbTrReimburseList = tbTrReimburseList;
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
        if (!(object instanceof TbMStatus)) {
            return false;
        }
        TbMStatus other = (TbMStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbMStatus[ id=" + id + " ]";
    }
    
}

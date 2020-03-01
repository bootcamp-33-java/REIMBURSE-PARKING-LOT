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
@Table(name = "tb_m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMEmployee.findAll", query = "SELECT t FROM TbMEmployee t")
    , @NamedQuery(name = "TbMEmployee.findById", query = "SELECT t FROM TbMEmployee t WHERE t.id = :id")
    , @NamedQuery(name = "TbMEmployee.findByName", query = "SELECT t FROM TbMEmployee t WHERE t.name = :name")
    , @NamedQuery(name = "TbMEmployee.findByEmail", query = "SELECT t FROM TbMEmployee t WHERE t.email = :email")
    , @NamedQuery(name = "TbMEmployee.findByIsActive", query = "SELECT t FROM TbMEmployee t WHERE t.isActive = :isActive")
    , @NamedQuery(name = "TbMEmployee.findByPhoneNumber", query = "SELECT t FROM TbMEmployee t WHERE t.phoneNumber = :phoneNumber")})
public class TbMEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "pic", fetch = FetchType.LAZY)
    private List<TbTrSite> tbTrSiteList;
    @JoinColumn(name = "site", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TbTrSite site;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalBy", fetch = FetchType.LAZY)
    private List<TbTrHistory> tbTrHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<TbMVehicle> tbMVehicleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<TbTrReimburse> tbTrReimburseList;

    public TbMEmployee() {
    }

    public TbMEmployee(String id) {
        this.id = id;
    }

    public TbMEmployee(String id, String name, String email, boolean isActive, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @XmlTransient
    public List<TbTrSite> getTbTrSiteList() {
        return tbTrSiteList;
    }

    public void setTbTrSiteList(List<TbTrSite> tbTrSiteList) {
        this.tbTrSiteList = tbTrSiteList;
    }

    public TbTrSite getSite() {
        return site;
    }

    public void setSite(TbTrSite site) {
        this.site = site;
    }

    @XmlTransient
    public List<TbTrHistory> getTbTrHistoryList() {
        return tbTrHistoryList;
    }

    public void setTbTrHistoryList(List<TbTrHistory> tbTrHistoryList) {
        this.tbTrHistoryList = tbTrHistoryList;
    }

    @XmlTransient
    public List<TbMVehicle> getTbMVehicleList() {
        return tbMVehicleList;
    }

    public void setTbMVehicleList(List<TbMVehicle> tbMVehicleList) {
        this.tbMVehicleList = tbMVehicleList;
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
        if (!(object instanceof TbMEmployee)) {
            return false;
        }
        TbMEmployee other = (TbMEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbMEmployee[ id=" + id + " ]";
    }
    
}

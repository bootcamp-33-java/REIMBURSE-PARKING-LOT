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
@Table(name = "tb_m_vehicle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMVehicle.findAll", query = "SELECT t FROM TbMVehicle t")
    , @NamedQuery(name = "TbMVehicle.findById", query = "SELECT t FROM TbMVehicle t WHERE t.id = :id")
    , @NamedQuery(name = "TbMVehicle.findByVehicleType", query = "SELECT t FROM TbMVehicle t WHERE t.vehicleType = :vehicleType")
    , @NamedQuery(name = "TbMVehicle.findByStnkOwner", query = "SELECT t FROM TbMVehicle t WHERE t.stnkOwner = :stnkOwner")
    , @NamedQuery(name = "TbMVehicle.findByPhotoStnk", query = "SELECT t FROM TbMVehicle t WHERE t.photoStnk = :photoStnk")})
public class TbMVehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "vehicle_type")
    private String vehicleType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "stnk_owner")
    private String stnkOwner;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "photo_stnk")
    private String photoStnk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<TbTrTicket> tbTrTicketList;
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMEmployee employee;

    public TbMVehicle() {
    }

    public TbMVehicle(String id) {
        this.id = id;
    }

    public TbMVehicle(String id, String vehicleType, String stnkOwner, String photoStnk) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.stnkOwner = stnkOwner;
        this.photoStnk = photoStnk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getStnkOwner() {
        return stnkOwner;
    }

    public void setStnkOwner(String stnkOwner) {
        this.stnkOwner = stnkOwner;
    }

    public String getPhotoStnk() {
        return photoStnk;
    }

    public void setPhotoStnk(String photoStnk) {
        this.photoStnk = photoStnk;
    }

    @XmlTransient
    public List<TbTrTicket> getTbTrTicketList() {
        return tbTrTicketList;
    }

    public void setTbTrTicketList(List<TbTrTicket> tbTrTicketList) {
        this.tbTrTicketList = tbTrTicketList;
    }

    public TbMEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(TbMEmployee employee) {
        this.employee = employee;
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
        if (!(object instanceof TbMVehicle)) {
            return false;
        }
        TbMVehicle other = (TbMVehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbMVehicle[ id=" + id + " ]";
    }
    
}

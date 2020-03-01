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
@Table(name = "tb_m_parking_lot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMParkingLot.findAll", query = "SELECT t FROM TbMParkingLot t")
    , @NamedQuery(name = "TbMParkingLot.findById", query = "SELECT t FROM TbMParkingLot t WHERE t.id = :id")
    , @NamedQuery(name = "TbMParkingLot.findByName", query = "SELECT t FROM TbMParkingLot t WHERE t.name = :name")
    , @NamedQuery(name = "TbMParkingLot.findByLocation", query = "SELECT t FROM TbMParkingLot t WHERE t.location = :location")
    , @NamedQuery(name = "TbMParkingLot.findByPhoneNumber", query = "SELECT t FROM TbMParkingLot t WHERE t.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "TbMParkingLot.findByDefaultPrice", query = "SELECT t FROM TbMParkingLot t WHERE t.defaultPrice = :defaultPrice")})
public class TbMParkingLot implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "location")
    private String location;
    @Size(max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "default_price")
    private int defaultPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parking", fetch = FetchType.LAZY)
    private List<TbTrTicket> tbTrTicketList;

    public TbMParkingLot() {
    }

    public TbMParkingLot(Integer id) {
        this.id = id;
    }

    public TbMParkingLot(Integer id, String name, String location, int defaultPrice) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.defaultPrice = defaultPrice;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    @XmlTransient
    public List<TbTrTicket> getTbTrTicketList() {
        return tbTrTicketList;
    }

    public void setTbTrTicketList(List<TbTrTicket> tbTrTicketList) {
        this.tbTrTicketList = tbTrTicketList;
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
        if (!(object instanceof TbMParkingLot)) {
            return false;
        }
        TbMParkingLot other = (TbMParkingLot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbMParkingLot[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Insane
 */
@Entity
@Table(name = "tb_tr_ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrTicket.findAll", query = "SELECT t FROM TbTrTicket t")
    , @NamedQuery(name = "TbTrTicket.findById", query = "SELECT t FROM TbTrTicket t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrTicket.findByUploadDate", query = "SELECT t FROM TbTrTicket t WHERE t.uploadDate = :uploadDate")
    , @NamedQuery(name = "TbTrTicket.findByPhotoTicket", query = "SELECT t FROM TbTrTicket t WHERE t.photoTicket = :photoTicket")
    , @NamedQuery(name = "TbTrTicket.findByPrice", query = "SELECT t FROM TbTrTicket t WHERE t.price = :price")})
public class TbTrTicket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upload_date")
    @Temporal(TemporalType.DATE)
    private Date uploadDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "photo_ticket")
    private String photoTicket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @JoinColumn(name = "vehicle", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMVehicle vehicle;
    @JoinColumn(name = "parking", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMParkingLot parking;
    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrReimburse reimburse;

    public TbTrTicket() {
    }

    public TbTrTicket(Integer id) {
        this.id = id;
    }

    public TbTrTicket(Integer id, Date uploadDate, String photoTicket, int price) {
        this.id = id;
        this.uploadDate = uploadDate;
        this.photoTicket = photoTicket;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPhotoTicket() {
        return photoTicket;
    }

    public void setPhotoTicket(String photoTicket) {
        this.photoTicket = photoTicket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TbMVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(TbMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TbMParkingLot getParking() {
        return parking;
    }

    public void setParking(TbMParkingLot parking) {
        this.parking = parking;
    }

    public TbTrReimburse getReimburse() {
        return reimburse;
    }

    public void setReimburse(TbTrReimburse reimburse) {
        this.reimburse = reimburse;
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
        if (!(object instanceof TbTrTicket)) {
            return false;
        }
        TbTrTicket other = (TbTrTicket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbTrTicket[ id=" + id + " ]";
    }
    
}

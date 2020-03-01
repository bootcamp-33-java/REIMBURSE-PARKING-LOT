/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Insane
 */
@Entity
@Table(name = "tb_tr_reimburse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrReimburse.findAll", query = "SELECT t FROM TbTrReimburse t")
    , @NamedQuery(name = "TbTrReimburse.findById", query = "SELECT t FROM TbTrReimburse t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrReimburse.findByStartDate", query = "SELECT t FROM TbTrReimburse t WHERE t.startDate = :startDate")
    , @NamedQuery(name = "TbTrReimburse.findByEndDate", query = "SELECT t FROM TbTrReimburse t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "TbTrReimburse.findByTotal", query = "SELECT t FROM TbTrReimburse t WHERE t.total = :total")
    , @NamedQuery(name = "TbTrReimburse.findByPeriod", query = "SELECT t FROM TbTrReimburse t WHERE t.period = :period")
    , @NamedQuery(name = "TbTrReimburse.findByNotes", query = "SELECT t FROM TbTrReimburse t WHERE t.notes = :notes")})
public class TbTrReimburse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private int total;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "period")
    private String period;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "notes")
    private String notes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reimburse", fetch = FetchType.LAZY)
    private List<TbTrTicket> tbTrTicketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reimburse", fetch = FetchType.LAZY)
    private List<TbTrHistory> tbTrHistoryList;
    @JoinColumn(name = "current_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus currentStatus;
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMEmployee employee;

    public TbTrReimburse() {
    }

    public TbTrReimburse(String id) {
        this.id = id;
    }

    public TbTrReimburse(String id, Date startDate, Date endDate, int total, String period, String notes) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.period = period;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @XmlTransient
    public List<TbTrTicket> getTbTrTicketList() {
        return tbTrTicketList;
    }

    public void setTbTrTicketList(List<TbTrTicket> tbTrTicketList) {
        this.tbTrTicketList = tbTrTicketList;
    }

    @XmlTransient
    public List<TbTrHistory> getTbTrHistoryList() {
        return tbTrHistoryList;
    }

    public void setTbTrHistoryList(List<TbTrHistory> tbTrHistoryList) {
        this.tbTrHistoryList = tbTrHistoryList;
    }

    public TbMStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(TbMStatus currentStatus) {
        this.currentStatus = currentStatus;
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
        if (!(object instanceof TbTrReimburse)) {
            return false;
        }
        TbTrReimburse other = (TbTrReimburse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbTrReimburse[ id=" + id + " ]";
    }
    
}

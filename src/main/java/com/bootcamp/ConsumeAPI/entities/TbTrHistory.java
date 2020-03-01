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
@Table(name = "tb_tr_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrHistory.findAll", query = "SELECT t FROM TbTrHistory t")
    , @NamedQuery(name = "TbTrHistory.findById", query = "SELECT t FROM TbTrHistory t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrHistory.findByNotes", query = "SELECT t FROM TbTrHistory t WHERE t.notes = :notes")
    , @NamedQuery(name = "TbTrHistory.findByHistoryDate", query = "SELECT t FROM TbTrHistory t WHERE t.historyDate = :historyDate")})
public class TbTrHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "history_date")
    @Temporal(TemporalType.DATE)
    private Date historyDate;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus status;
    @JoinColumn(name = "approval_by", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMEmployee approvalBy;
    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrReimburse reimburse;

    public TbTrHistory() {
    }

    public TbTrHistory(Integer id) {
        this.id = id;
    }

    public TbTrHistory(Integer id, String notes, Date historyDate) {
        this.id = id;
        this.notes = notes;
        this.historyDate = historyDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public TbMStatus getStatus() {
        return status;
    }

    public void setStatus(TbMStatus status) {
        this.status = status;
    }

    public TbMEmployee getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(TbMEmployee approvalBy) {
        this.approvalBy = approvalBy;
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
        if (!(object instanceof TbTrHistory)) {
            return false;
        }
        TbTrHistory other = (TbTrHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.ConsumeAPI.entities.TbTrHistory[ id=" + id + " ]";
    }
    
}

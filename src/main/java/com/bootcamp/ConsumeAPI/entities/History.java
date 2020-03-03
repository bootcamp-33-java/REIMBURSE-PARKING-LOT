/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Yuyun
 */
@Entity
@Table(name = "tb_tr_history")
@XmlRootElement
@Data
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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
    private Status status;

    @JoinColumn(name = "approval_by", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee approvalBy;

    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Reimburse reimburse;


}

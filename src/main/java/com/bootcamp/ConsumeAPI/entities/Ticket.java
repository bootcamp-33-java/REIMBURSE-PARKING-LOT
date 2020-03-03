/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Yuyun
 */
@Entity
@Table(name = "tb_tr_ticket")
@Data
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "upload_date")
    private LocalDate uploadDate;

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
    private Vehicle vehicle;

    @JoinColumn(name = "parking", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParkingLot parking;

    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Reimburse reimburse;


}

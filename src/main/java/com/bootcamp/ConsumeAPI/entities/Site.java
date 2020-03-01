/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yuyun
 */
@Entity
@Table(name = "tb_tr_site")
@Data
public class Site implements Serializable {

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
    private Employee pic;

    
}

package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReimburseDto implements Serializable {
    private Ticket ticket;

    private Employee employee;
}

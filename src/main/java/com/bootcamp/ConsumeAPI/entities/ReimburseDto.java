package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;

@Data
public class ReimburseDto {
    private Ticket ticket;

    private String employeeId;
}

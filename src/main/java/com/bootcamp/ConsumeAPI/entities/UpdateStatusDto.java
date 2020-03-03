package com.bootcamp.ConsumeAPI.entities;

import lombok.Data;

@Data
public class UpdateStatusDto {

    private String id;

    private String role;

    private String status;
}

package com.lab.neko.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NekoQueryResponseDto {
    private UUID id;
    private String fullName;
    private String gender;
    private String color;
    private String description;
    private Date createdAt;
    private String createdBy;
}

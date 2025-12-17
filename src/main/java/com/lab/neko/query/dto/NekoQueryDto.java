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
public class NekoQueryDto {
    private UUID id;
    private long version;
    private String fullname;
    private String gender;
    private String color;
    private String description;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}

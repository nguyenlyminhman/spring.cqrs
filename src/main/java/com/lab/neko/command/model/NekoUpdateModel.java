package com.lab.neko.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NekoUpdateModel {
    private String id;
    private String fullName;
    private String gender;
    private String color;
    private String description;
}

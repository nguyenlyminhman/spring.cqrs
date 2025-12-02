package com.lab.neko.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NekoCreateModel {
    private String fullName;
    private String gender;
    private String color;
    private String description;
}

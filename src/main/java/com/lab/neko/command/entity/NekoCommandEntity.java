package com.lab.neko.command.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lab.neko.command.valueObject.NekoCreateVO;
import jakarta.persistence.*;
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
@Entity
@Table(name = "neko_command")
public class NekoCommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Version
    private long version;

    @Column(name = "fullname")
    private String fullName;

    private String gender;
    private String color;
    private String description;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+07:00")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    public NekoCommandEntity(NekoCreateVO vo) {
        this.fullName = vo.getFullName();
        this.gender = vo.getGender();
        this.color = vo.getColor();
        this.description = vo.getDescription();
    }
}

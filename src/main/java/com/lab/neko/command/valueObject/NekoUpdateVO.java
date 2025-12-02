package com.lab.neko.command.valueObject;

import com.lab.neko.command.model.NekoUpdateModel;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Setter
public class NekoUpdateVO {
    private UUID id;
    private String fullName;
    private String gender;
    private String color;
    private String description;

    private static final List<String> ALLOWED_GENDERS = Arrays.asList("MALE", "FEMALE", "OTHER");

    private NekoUpdateVO(UUID id, String fullName, String gender, String color, String description) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.color = color;
        this.description = description;
    }

    public static NekoUpdateVO fromModel(NekoUpdateModel model) {
        return new NekoUpdateVO(
                UUID.fromString(model.getId()),
                model.getFullName(),
                model.getGender(),
                model.getColor(),
                model.getDescription()
        );
    }

    public static NekoUpdateVO ofFields(UUID id, String fullName, String gender, String color, String description) {
        validate(id, fullName, gender, color);
        return new NekoUpdateVO(id, fullName, gender, color, description);
    }

    // Validation rules
    private static void validate(UUID id, String fullName, String gender, String color) {
        if (id == null || "".equals(String.valueOf(id))) {
            throw new IllegalArgumentException("id must not be empty");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName must not be empty");
        }
        if (gender == null || !ALLOWED_GENDERS.contains(gender.toUpperCase())) {
            throw new IllegalArgumentException("gender must be one of " + ALLOWED_GENDERS);
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("color must not be empty");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
}

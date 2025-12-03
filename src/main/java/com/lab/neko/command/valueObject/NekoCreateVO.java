package com.lab.neko.command.valueObject;

import com.lab.neko.command.model.NekoCreateModel;
import java.util.Arrays;
import java.util.List;


public final class NekoCreateVO {

    private final String fullName;
    private final String gender;
    private final String color;
    private final String description;

    private static final List<String> ALLOWED_GENDERS = Arrays.asList("MALE", "FEMALE", "OTHER");

    public NekoCreateVO(String fullName, String gender, String color, String description) {
        this.fullName = fullName;
        this.gender = gender;
        this.color = color;
        this.description = description;
    }

    public static NekoCreateVO fromModel(NekoCreateModel model) {
        return new NekoCreateVO(
                model.getFullName(),
                model.getGender(),
                model.getColor(),
                model.getDescription()
        );
    }

    public static NekoCreateVO ofFields(String fullName, String gender, String color, String description) {
        validate(fullName, gender, color);
        return new NekoCreateVO(fullName, gender, color, description);
    }

    // Validation rules
    private static void validate(String fullName, String gender, String color) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName must not be empty");
        }
        if (gender == null || !ALLOWED_GENDERS.contains(gender.toUpperCase())) {
            throw new IllegalArgumentException("gender must be one of" + ALLOWED_GENDERS);
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("color must not be empty");
        }
    }

    // Getters
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

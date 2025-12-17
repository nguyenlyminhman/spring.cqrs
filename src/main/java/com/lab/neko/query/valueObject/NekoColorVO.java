package com.lab.neko.query.valueObject;

import com.lab.neko.query.model.NekoColorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NekoColorVO {
    private String color;

    public NekoColorVO(String color) {
        this.color = color;
    }

    public static NekoColorVO fromModel(NekoColorModel model) {
        return new NekoColorVO(model.getColor());
    }

    public static NekoColorVO ofFields(String color) {
        validate(color);
        return new NekoColorVO(color);
    }

    private static void validate(String color) {
        if (color == null || "".equals(String.valueOf(color))) {
            throw new IllegalArgumentException("color must not be empty");
        }
    }
}

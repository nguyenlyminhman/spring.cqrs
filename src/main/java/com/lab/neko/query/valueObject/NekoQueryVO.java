package com.lab.neko.query.valueObject;

import com.lab.neko.query.model.NekoQueryModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NekoQueryVO {
    private UUID id;

    public NekoQueryVO(UUID id) {
        this.id = id;
    }

    public static NekoQueryVO fromModel(NekoQueryModel model) {
        return new NekoQueryVO(model.getId());
    }

    public static NekoQueryVO ofFields(UUID id) {
        validate(id);
        return new NekoQueryVO(id);
    }

    private static void validate(UUID id) {
        if (id == null || "".equals(String.valueOf(id))) {
            throw new IllegalArgumentException("id must not be empty");
        }
    }
}

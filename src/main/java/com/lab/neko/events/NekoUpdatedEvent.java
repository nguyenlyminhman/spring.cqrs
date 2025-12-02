package com.lab.neko.events;

import com.lab.neko.command.entity.NekoCommandEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NekoUpdatedEvent {
    private NekoCommandEntity nekoCommandEntity;
}

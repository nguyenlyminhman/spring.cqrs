package com.lab.neko.event;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.events.NekoCreatedEvent;
import com.lab.neko.events.NekoUpdatedEvent;
import com.lab.neko.events.ProjectionHandler;
import com.lab.utils.RedisKey;
import com.lab.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProjectionHandlerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RedisUtils redisUtils;

    @InjectMocks
    private ProjectionHandler projectionHandler;

    @Test
    void shouldCreateSuccessfully() {

        UUID idFake = UUID.randomUUID();
        NekoCommandEntity commandEntity = new NekoCommandEntity();
        commandEntity.setId(idFake);
        commandEntity.setFullName("Neko");
        commandEntity.setColor("Black");
        commandEntity.setGender("MALE");
        commandEntity.setDescription("Naughty cat");

        NekoCreatedEvent event = new NekoCreatedEvent(commandEntity);
        String keyCache = RedisKey.nekoProfile(commandEntity.getId().toString());

        projectionHandler.onCreate(event);

        verify(redisUtils).setCacheObject(keyCache, commandEntity);
    }

    @Test
    void shouldUpdateSuccessfully() {
        UUID idFake = UUID.randomUUID();
        NekoCommandEntity commandEntity = new NekoCommandEntity();
        commandEntity.setId(idFake);
        commandEntity.setFullName("Update Neko");
        commandEntity.setColor("Black");
        commandEntity.setGender("MALE");
        commandEntity.setDescription("Silly cat");

        NekoUpdatedEvent event = new NekoUpdatedEvent(commandEntity);
        UUID id = event.getNekoCommandEntity().getId();
        //
        String keyCache = RedisKey.nekoProfile(id.toString());
        projectionHandler.onUpdate(event);
        verify(redisUtils).setCacheObject(keyCache, commandEntity);
    }
}

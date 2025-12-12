package com.lab.neko.event;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.events.NekoCreatedEvent;
import com.lab.neko.events.NekoUpdatedEvent;
import com.lab.neko.events.ProjectionHandler;
import com.lab.neko.query.entity.NekoQueryEntity;
import com.lab.neko.query.repository.NekoQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProjectionHandlerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NekoQueryRepository queryRepository;

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

        NekoQueryEntity mappedEntity = new NekoQueryEntity();

        when(modelMapper.map( commandEntity, NekoQueryEntity.class)).thenReturn(mappedEntity);

        projectionHandler.onCreate(event);

        verify(modelMapper, times(1)).map( commandEntity, NekoQueryEntity.class);
        verify(queryRepository, times(1)).save(mappedEntity);
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

        NekoQueryEntity  existNeko = new NekoQueryEntity ();
        existNeko.setId(id);
        existNeko.setFullName("Old Neko");
        existNeko.setColor("Black");
        existNeko.setGender("MALE");
        existNeko.setDescription("Silly cat");
        existNeko.setUpdatedAt(new Date());
        existNeko.setUpdatedBy("USER");


        when(queryRepository.findById(id)).thenReturn( Optional.of(existNeko));

        //
        projectionHandler.onUpdate(event);

        verify(queryRepository, times(1)).findById(id);
        verify(queryRepository, times(1)).save(existNeko);

        assertEquals(idFake, existNeko.getId());
        assertEquals("Update Neko", existNeko.getFullName());
        assertEquals("Black", existNeko.getColor());
        assertEquals("MALE", existNeko.getGender());
        assertEquals("Silly cat", existNeko.getDescription());

        assertNotNull(existNeko.getUpdatedAt());
        assertEquals("USER", existNeko.getUpdatedBy());
    }

    @Test
    void shouldNotFoundWhenUpdate() {
        UUID idFake = UUID.randomUUID();
        NekoCommandEntity commandEntity = new NekoCommandEntity();
        commandEntity.setId(idFake);
        commandEntity.setFullName("Update Neko");
        commandEntity.setColor("Black");
        commandEntity.setGender("MALE");
        commandEntity.setDescription("Silly cat");

        NekoUpdatedEvent event = new NekoUpdatedEvent(commandEntity);

        UUID id = event.getNekoCommandEntity().getId();

        when(queryRepository.findById(id)).thenReturn( Optional.empty());

        assertThrows(NoSuchElementException.class, ()-> {projectionHandler.onUpdate(event);});

        verify(queryRepository, never()).save(any());
    }

}

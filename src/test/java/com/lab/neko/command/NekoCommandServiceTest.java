package com.lab.neko.command;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.repository.NekoCommandRepository;
import com.lab.neko.command.service.impl.NekoCommandServiceImpl;
import com.lab.neko.events.NekoCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NekoCommandServiceTest {

    @Mock
    private NekoCommandRepository commandRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private NekoCommandServiceImpl service;

    @Test
    void shouldCreateNekoSuccessfully() throws Exception {

        NekoCreateModel model = new NekoCreateModel("Neko", "MALE", "Black", "Cute Cat");
        UUID fakeId = UUID.randomUUID();

        when(commandRepository.save(any(NekoCommandEntity.class)))
                .thenAnswer(invocation -> {
                    NekoCommandEntity e = invocation.getArgument(0);
                    e.setId(fakeId);
                    return e;
                });

        UUID result = service.create(model);

        assertNotNull(result);
        assertEquals(fakeId, result);

        verify(commandRepository).save(any());
        verify(publisher).publishEvent(any(NekoCreatedEvent.class));
    }

    @Test
    void shouldReturnFullNameMustNotBeEmpty() throws Exception {

        NekoCreateModel model = new NekoCreateModel("", "MALE", "Black", "Cute Cat");

        Exception ex = assertThrows(Exception.class, () -> service.create(model));
        assertTrue(ex.getMessage().contains("fullName must not be empty"));

        verify(commandRepository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }

    @Test
    void shouldGenderMustBeOne() throws Exception {

        NekoCreateModel model = new NekoCreateModel("Neko", "M", "Black", "Cute Cat");

        Exception ex = assertThrows(Exception.class, () -> service.create(model));
        assertTrue(ex.getMessage().contains("gender must be one"));

        verify(commandRepository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }

    @Test
    void shouldReturnColorMustNotBeEmpty() throws Exception {

        NekoCreateModel model = new NekoCreateModel("Neko", "MALE", "", "Cute Cat");

        Exception ex = assertThrows(Exception.class, () -> service.create(model));
        assertTrue(ex.getMessage().contains("color must not be empty"));

        verify(commandRepository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }
}

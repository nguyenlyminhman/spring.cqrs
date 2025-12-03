package com.lab.neko.command;

import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.valueObject.NekoCreateVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NekoCreateVOTest {
    @Test
    void shouldCreateVO_WhenValidFields() {
        NekoCreateModel model = new NekoCreateModel("Neko", "MALE", "Black", "Cute Cat");
        NekoCreateVO vo = NekoCreateVO.ofFields( model.getFullName(),  model.getGender(), model.getColor(), model.getDescription());

        assertEquals("Neko", vo.getFullName());
        assertEquals("MALE", vo.getGender());
        assertEquals("Black", vo.getColor());
        assertEquals("Cute Cat", vo.getDescription());
    }


    @Test
    void shouldThrowInvalidFullname() throws Exception {
        NekoCreateModel model = new NekoCreateModel("", "MALE", "Black", "Cute Cat");
        Exception ex = assertThrows(Exception.class, () -> NekoCreateVO.ofFields( model.getFullName(),  model.getGender(),
                model.getColor(), model.getDescription()));
        assertTrue(ex.getMessage().contains("fullName must not be empty"));
    }

    @Test
    void shouldThrowInvalidGender() throws Exception {
        NekoCreateModel model = new NekoCreateModel("Neko", "M", "Black", "Cute Cat");
        Exception ex = assertThrows(Exception.class, () -> NekoCreateVO.ofFields( model.getFullName(),  model.getGender(),
                model.getColor(), model.getDescription()));
        assertTrue(ex.getMessage().contains("gender must be one of"));
    }

    @Test
    void shouldThrowInvalidColor() throws Exception {
        NekoCreateModel model = new NekoCreateModel("Neko", "MALE", "", "Cute Cat");
        Exception ex = assertThrows(Exception.class, () -> NekoCreateVO.ofFields( model.getFullName(),  model.getGender(),
                model.getColor(), model.getDescription()));
        assertTrue(ex.getMessage().contains("color must not be empty"));
    }
}

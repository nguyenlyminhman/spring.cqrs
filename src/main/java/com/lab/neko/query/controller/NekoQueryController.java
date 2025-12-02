package com.lab.neko.query.controller;

import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.model.NekoUpdateModel;
import com.lab.neko.query.dto.NekoQueryResponseDto;
import com.lab.neko.query.entity.NekoQueryEntity;
import com.lab.neko.query.service.INekoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/neko")
public class NekoQueryController {

    @Autowired
    private INekoQueryService queryService;

    @GetMapping("/all")
    public ResponseEntity<?> all() throws Exception {
        try {
            List<NekoQueryEntity> list = queryService.findAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> update(@RequestParam(value = "id") String id) {
        try {
            NekoQueryResponseDto responseDto = queryService.findNekoById(id);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

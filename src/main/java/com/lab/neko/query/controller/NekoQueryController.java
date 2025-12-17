package com.lab.neko.query.controller;

import com.lab.neko.query.dto.NekoQueryDto;
import com.lab.neko.query.dto.NekoQueryResponseDto;
import com.lab.neko.query.service.INekoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/neko")
public class NekoQueryController {

    @Autowired
    private INekoQueryService queryService;

    @GetMapping("/all")
    public ResponseEntity<?> all() throws Exception {
        try {
            List<NekoQueryDto> list = queryService.findAll();
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

    @GetMapping("/list")
    public ResponseEntity<?> byColor(@RequestParam( value = "color") String color) throws Exception {
        try {
            List<NekoQueryDto> list = queryService.findNekoByColor(color);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

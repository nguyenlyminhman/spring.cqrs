package com.lab.neko.command.controller;

import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.model.NekoUpdateModel;
import com.lab.neko.command.service.INekoCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/neko")
public class NekoCommandController {

    @Autowired
    private INekoCommandService service;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody NekoCreateModel req) throws Exception {
        try {
            UUID createdId = service.create(req);
            return ResponseEntity.ok(createdId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody NekoUpdateModel req) {
        try {
            UUID createdId = service.update(req);
            return ResponseEntity.ok(createdId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

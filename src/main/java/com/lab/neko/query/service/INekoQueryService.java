package com.lab.neko.query.service;

import com.lab.neko.query.dto.NekoQueryResponseDto;
import com.lab.neko.query.entity.NekoQueryEntity;

import java.util.List;
import java.util.UUID;

public interface INekoQueryService {

    NekoQueryResponseDto findNekoById(String uuid);

    List<NekoQueryEntity> findAll();
}

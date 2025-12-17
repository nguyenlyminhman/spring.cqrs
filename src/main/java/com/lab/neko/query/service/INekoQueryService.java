package com.lab.neko.query.service;

import com.lab.neko.query.dto.NekoQueryDto;
import com.lab.neko.query.dto.NekoQueryResponseDto;

import java.util.List;

public interface INekoQueryService {

    NekoQueryResponseDto findNekoById(String uuid);

    List<NekoQueryDto> findAll();

    List<NekoQueryDto> findNekoByColor(String color);
}

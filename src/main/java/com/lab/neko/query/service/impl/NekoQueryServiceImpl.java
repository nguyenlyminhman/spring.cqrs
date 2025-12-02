package com.lab.neko.query.service.impl;

import com.lab.neko.query.dto.NekoQueryResponseDto;
import com.lab.neko.query.entity.NekoQueryEntity;
import com.lab.neko.query.repository.NekoQueryRepository;
import com.lab.neko.query.service.INekoQueryService;
import com.lab.neko.query.valueObject.NekoQueryVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NekoQueryServiceImpl implements INekoQueryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NekoQueryRepository queryRepository;

    @Override
    public NekoQueryResponseDto findNekoById(String uuid) {
        NekoQueryEntity nekoQueryEntity = new NekoQueryEntity();
        try {
            UUID id = UUID.fromString(uuid);
            NekoQueryVO queryVO = NekoQueryVO.ofFields(id);
            Optional<NekoQueryEntity> rs = queryRepository.findById(queryVO.getId());
            if (rs.isPresent()) {
                nekoQueryEntity = rs.get();
            }
            NekoQueryResponseDto responseDto = modelMapper.map(nekoQueryEntity, NekoQueryResponseDto.class);
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<NekoQueryEntity> findAll() {
        return queryRepository.findAll();
    }
}

package com.lab.neko.query.service.impl;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.command.repository.NekoCommandRepository;
import com.lab.neko.query.dto.NekoQueryDto;
import com.lab.neko.query.dto.NekoQueryResponseDto;
import com.lab.neko.query.service.INekoQueryService;
import com.lab.neko.query.valueObject.NekoColorVO;
import com.lab.neko.query.valueObject.NekoQueryVO;
import com.lab.utils.RedisKey;
import com.lab.utils.RedisUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NekoQueryServiceImpl implements INekoQueryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NekoCommandRepository nekoCommandRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public NekoQueryResponseDto findNekoById(String uuid) {
        try {
            NekoCommandEntity commandEntity = new NekoCommandEntity();
            UUID id = UUID.fromString(uuid);
            NekoQueryVO queryVO = NekoQueryVO.ofFields(id);

            String redisNekoKey = RedisKey.nekoProfile(queryVO.getId().toString());
            commandEntity = (NekoCommandEntity) redisUtils.getCacheObject(redisNekoKey);

            if (Objects.isNull(commandEntity)) {
                Optional<NekoCommandEntity> existNeko = nekoCommandRepository.findById(id);
                if(existNeko.isPresent()) {
                    commandEntity = existNeko.get();
                    redisUtils.setCacheObject(redisNekoKey, commandEntity);
                }
            }

            NekoQueryResponseDto responseDto =  modelMapper.map(commandEntity, NekoQueryResponseDto.class);

            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<NekoQueryDto> findAll() {
        List<NekoQueryDto> rsFn = new ArrayList<>();
        List<NekoCommandEntity> nekoCommandList = redisUtils.scanOptionsList("app:neko:profile:*", 1000);

        nekoCommandList.forEach(nekoCommandItem -> {
            NekoQueryDto dto = modelMapper.map(nekoCommandItem, NekoQueryDto.class);
            rsFn.add(dto);
        });

        return rsFn;
    }

    @Override
    public List<NekoQueryDto> findNekoByColor(String color) {
        List<NekoQueryDto> rsFn = new ArrayList<>();
        NekoColorVO colorVO = NekoColorVO.ofFields(color);
        Cursor<byte[]> nekoCommandCursor = redisUtils.scanOptionsCursor("app:neko:profile:*", 1000);

        while (nekoCommandCursor.hasNext()) {
            String key = new String(nekoCommandCursor.next());
            NekoCommandEntity commandEntity = (NekoCommandEntity) redisUtils.getCacheObject(key);
            if (commandEntity.getColor().toLowerCase().contains(colorVO.getColor().toLowerCase())) {
                NekoQueryDto dto = modelMapper.map(commandEntity, NekoQueryDto.class);
                rsFn.add(dto);
            }
        }

        return rsFn;
    }
}

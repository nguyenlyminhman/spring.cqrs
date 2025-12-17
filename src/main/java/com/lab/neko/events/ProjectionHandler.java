package com.lab.neko.events;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.query.dto.NekoQueryDto;
import com.lab.utils.RedisKey;
import com.lab.utils.RedisUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectionHandler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisUtils redisUtils;

    @EventListener
    public void onCreate(NekoCreatedEvent event) {
        NekoCommandEntity commandEntity = event.getNekoCommandEntity();
        System.out.println("- - - - - NekoCreatedEvent - Begin - - - - -");
        String nekoId = commandEntity.getId().toString();
        String key = RedisKey.nekoProfile(nekoId);

        redisUtils.setCacheObject(key, commandEntity);
        System.out.println("- - - - - NekoCreatedEvent - End - - - - -");
    }

    @EventListener
    public void onUpdate(NekoUpdatedEvent event) {
        NekoCommandEntity commandEntity = event.getNekoCommandEntity();
        System.out.println("- - - - - NekoUpdatedEvent - Begin - - - - -");
        NekoQueryDto queryEntity = new NekoQueryDto();
        queryEntity.setId(commandEntity.getId());
        queryEntity.setVersion(commandEntity.getVersion());
        queryEntity.setFullname(commandEntity.getFullName());
        queryEntity.setGender(commandEntity.getGender());
        queryEntity.setColor(commandEntity.getColor());
        queryEntity.setDescription(commandEntity.getDescription());

        queryEntity.setUpdatedAt(new Date());
        queryEntity.setUpdatedBy("USER");

        String nekoId = commandEntity.getId().toString();
        String key = RedisKey.nekoProfile(nekoId);

        redisUtils.setCacheObject(key, queryEntity);
        System.out.println("- - - - - NekoUpdatedEvent - End - - - - -");
    }
}

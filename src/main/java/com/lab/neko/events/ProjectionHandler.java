package com.lab.neko.events;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.query.entity.NekoQueryEntity;
import com.lab.neko.query.repository.NekoQueryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class ProjectionHandler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NekoQueryRepository queryRepository;

    @EventListener
    public void onCreate(NekoCreatedEvent event) {
        NekoCommandEntity commandEntity = event.getNekoCommandEntity();
        System.out.println("- - - - - NekoCreatedEvent - Begin - - - - -");
        NekoQueryEntity queryEntity = modelMapper.map(commandEntity, NekoQueryEntity.class);

        queryRepository.save(queryEntity);
        System.out.println("- - - - - NekoCreatedEvent - End - - - - -");
    }

    @EventListener
    public void onUpdate(NekoUpdatedEvent event) {

        NekoCommandEntity updateEntity = event.getNekoCommandEntity();
        System.out.println("- - - - - NekoUpdatedEvent - Begin - - - - -");
        UUID id = updateEntity.getId();
        NekoQueryEntity queryEntity = queryRepository.findById(id).orElseThrow();

        queryEntity.setFullName(updateEntity.getFullName());
        queryEntity.setGender(updateEntity.getGender());
        queryEntity.setColor(updateEntity.getColor());
        queryEntity.setDescription(updateEntity.getDescription());

        queryEntity.setUpdatedAt(new Date());
        queryEntity.setUpdatedBy("USER");

        queryRepository.save(queryEntity);


    }
}

package com.lab.neko.command.service.impl;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.model.NekoUpdateModel;
import com.lab.neko.command.repository.NekoCommandRepository;
import com.lab.neko.command.service.INekoCommandService;
import com.lab.neko.command.valueObject.NekoCreateVO;
import com.lab.neko.command.valueObject.NekoUpdateVO;
import com.lab.neko.events.NekoCreatedEvent;
import com.lab.neko.events.NekoUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class NekoCommandServiceImpl implements INekoCommandService {

    @Autowired
    private NekoCommandRepository commandRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public UUID create(NekoCreateModel model) throws Exception {
        try {

            // no-validate => fromModel()
            // NekoCreateVO nekoCreateVO = NekoCreateVO.fromModel(model);
            // validate => ofFields()
            NekoCreateVO nekoCreateVO = NekoCreateVO.ofFields(model.getFullName(), model.getGender(), model.getColor(), model.getDescription());
            NekoCommandEntity entity = new NekoCommandEntity(nekoCreateVO);

            entity.setCreatedBy("SYSTEM");
            entity.setCreatedAt(new Date());

            commandRepository.save(entity);

            publisher.publishEvent(new NekoCreatedEvent(entity));

            return entity.getId();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UUID update(NekoUpdateModel model) {
        try {
            UUID id = UUID.fromString(model.getId());

            NekoUpdateVO nekoUpdateVO = NekoUpdateVO.ofFields(id, model.getFullName(), model.getGender(), model.getColor(), model.getDescription());
            NekoCommandEntity entity = commandRepository.findById(id).orElseThrow();

            entity.setFullName(nekoUpdateVO.getFullName());
            entity.setGender(nekoUpdateVO.getGender());
            entity.setColor(nekoUpdateVO.getColor());
            entity.setDescription(nekoUpdateVO.getDescription());

            commandRepository.save(entity);
            publisher.publishEvent(new NekoUpdatedEvent(entity));

            return entity.getId();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

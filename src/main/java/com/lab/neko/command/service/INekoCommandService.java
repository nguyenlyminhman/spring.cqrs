package com.lab.neko.command.service;

import com.lab.neko.command.model.NekoCreateModel;
import com.lab.neko.command.model.NekoUpdateModel;

import java.util.UUID;

public interface INekoCommandService {
    UUID create(NekoCreateModel nekoCreateModel) throws Exception;

    UUID update(NekoUpdateModel nekoCreateModel);
}

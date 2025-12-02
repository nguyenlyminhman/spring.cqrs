package com.lab.neko.command.repository;

import com.lab.neko.command.entity.NekoCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NekoCommandRepository extends JpaRepository<NekoCommandEntity, UUID> {
}

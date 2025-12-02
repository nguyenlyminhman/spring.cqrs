package com.lab.neko.query.repository;

import com.lab.neko.query.entity.NekoQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NekoQueryRepository extends JpaRepository<NekoQueryEntity, UUID> {
}

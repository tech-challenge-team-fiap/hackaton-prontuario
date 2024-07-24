package com.fiap.hackatonprontuario.repository;

import com.fiap.hackatonprontuario.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}


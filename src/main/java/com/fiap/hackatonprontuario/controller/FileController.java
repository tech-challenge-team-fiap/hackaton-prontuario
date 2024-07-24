package com.fiap.hackatonprontuario.controller;

import com.fiap.hackatonprontuario.model.FileEntity;
import com.fiap.hackatonprontuario.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam() MultipartFile file) {
        try {
            fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        Optional<FileEntity> fileEntity = fileService.getFile(id);
        if (fileEntity.isPresent()) {
            FileEntity file = fileEntity.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                    .contentType(org.springframework.http.MediaType.parseMediaType(file.getFileType()))
                    .body(new ByteArrayResource(file.getData()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


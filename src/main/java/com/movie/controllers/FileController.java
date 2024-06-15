package com.movie.controllers;

import com.movie.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded: " + uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(
            @PathVariable String fileName,
            @RequestParam(name = "download", defaultValue = "false") boolean download,
            HttpServletResponse response) throws IOException {
        try {
            InputStream resourceFile = fileService.getResourceFile(path, fileName);

            if (download) {
                // Set the content type of the response to binary data
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                // Set the content disposition to attachment to force download
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
            } else {
                // Set the content type of the response to image
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
            }

            // Copy the file stream to the response output stream
            StreamUtils.copy(resourceFile, response.getOutputStream());
        } catch (FileNotFoundException e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "File not found");
        }
    }
}

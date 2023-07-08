package com.idea.MyBook.Controller;
import com.idea.MyBook.Model.UploadResponse;
import com.idea.MyBook.Service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadr")
    public ResponseEntity<UploadResponse> uploadrFile( @RequestParam(name = "file", required = false) MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        return ResponseEntity.ok().body(uploadResponse);
    }
    @PostMapping("/upload")
    public String uploadFile( @RequestParam(name = "file", required = false) MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        ResponseEntity.ok().body(uploadResponse);
        return fileName;
    }
}

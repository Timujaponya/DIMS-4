package com.sau.bm.controller;

import com.sau.bm.model.Customer;
import com.sau.bm.repository.CustomerRepository;
import com.sau.bm.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/upload/{customerId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long customerId,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String filename = fileStorageService.saveFile(file);

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.setImageName(filename);
        customerRepository.save(customer);

        return ResponseEntity.ok("Yükleme başarılı: " + filename);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String filename) throws IOException {
        byte[] image = fileStorageService.loadFile(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}

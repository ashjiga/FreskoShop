
package com.fresko.service;

import org.springframework.web.multipart.MultipartFile;


public interface FirebaseStorageService {
    String uploadFile(MultipartFile file, String folder);
    void deleteByUrl(String url);
}

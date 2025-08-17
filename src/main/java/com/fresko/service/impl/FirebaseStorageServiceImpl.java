package com.fresko.service.impl;

import com.fresko.service.FirebaseStorageService;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    private final FirebaseApp app;
    private final String bucketName;
    private final String defaultFolder;

    public FirebaseStorageServiceImpl(
            FirebaseApp app,
            @Value("${firebase.bucket-name}") String bucketName,
            @Value("${firebase.folder}") String folder) {
        this.app = app;
        this.bucketName = bucketName;
        this.defaultFolder = folder;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        try {
            String f = (folder == null || folder.isBlank()) ? defaultFolder : folder;
            String ext = getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
            String objectName = f + "/" + name;

            Bucket bucket = StorageClient.getInstance(app).bucket(bucketName);
            Blob blob = bucket.create(objectName, file.getBytes(), file.getContentType());
            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            return "https://storage.googleapis.com/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException("Error subiendo archivo a Firebase Storage", e);
        }
    }

    @Override
    public void deleteByUrl(String url) {
        if (url == null || url.isBlank()) return;
        String prefix = "https://storage.googleapis.com/" + bucketName + "/";
        if (!url.startsWith(prefix)) return;

        String objectName = URLDecoder.decode(url.substring(prefix.length()), StandardCharsets.UTF_8);
        Bucket bucket = StorageClient.getInstance(app).bucket(bucketName);
        Blob blob = bucket.get(objectName);
        if (blob != null) blob.delete();
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int i = filename.lastIndexOf('.');
        return (i == -1) ? "" : filename.substring(i + 1);
    }
}
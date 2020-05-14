package com.project.ewalet.controller;

import com.project.ewalet.mapper.FileUploadMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.FileUpload;
import com.project.ewalet.model.User;
import com.project.ewalet.utils.Utility;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Autowired
    private Utility utility;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileUploadMapper fileUploadMapper;

    private static String UPLOAD_FOLDER = "E:/uploads/";

    @PostMapping("/upload-transfer-receipt")
    public ResponseEntity<?> fileUpload(@RequestParam("transfer_receipt") MultipartFile file, Authentication authentication) {
        JSONObject jsonObject = new JSONObject();
        User userProfile = userMapper.findByPhoneNumber(authentication.getName());

        //TODO validasi file size maks 2 Mb
        if (file.isEmpty()) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "File didn't exist");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = null;
            try {
                // read and write the file to the slelected location-
                byte[] bytes = file.getBytes();
                path = Paths.get(UPLOAD_FOLDER + utility.generateString() + "." + ext);
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int fileType = 0;
            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
                fileType = 1;
            } else if (ext.equalsIgnoreCase("png")) {
                fileType = 2;
            }

            FileUpload fileUpload = new FileUpload();
            fileUpload.setFile_type(fileType);
            fileUpload.setUser_id(userProfile.getId());
            fileUpload.setPath(path.toString());
            // save to db
            fileUploadMapper.save(fileUpload);

            jsonObject.put("status", 201);
            jsonObject.put("message", "File Uploaded sucessfully");

            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
    }
}

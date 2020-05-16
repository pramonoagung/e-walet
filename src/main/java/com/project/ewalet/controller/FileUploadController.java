package com.project.ewalet.controller;

import com.project.ewalet.mapper.FileUploadMapper;
import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserBalanceMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.FileUpload;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import com.project.ewalet.model.UserBalance;
import com.project.ewalet.service.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileUploadMapper fileUploadMapper;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload-transfer-receipt/{token}")
    public ResponseEntity<?> uploadFile(@RequestParam("transfer_receipt") MultipartFile file, @PathVariable String token,
                                        Authentication authentication) {
        JSONObject jsonObject = new JSONObject();
        User userProfile = userMapper.findByPhoneNumber(authentication.getName());
        if (file.isEmpty()) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "File didn't exist");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } if (file.getSize() < 2097152) {
            jsonObject.put("status", 406);
            jsonObject.put("message", "Max file size is 2 MB");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            int fileType = 0;
            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
                fileType = 1;
            } else if (ext.equalsIgnoreCase("png")) {
                fileType = 2;
            } else {
                jsonObject.put("status", 406);
                jsonObject.put("message", "File Not Acceptable ");
                return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
            }

            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            FileUpload fileUpload = new FileUpload();
            fileUpload.setFile_type(fileType);
            fileUpload.setUser_id(userProfile.getId());
            fileUpload.setPath(fileDownloadUri);
            fileUpload.setFile_name(fileName);
            // save to db
            int fileId = fileUploadMapper.save(fileUpload);

            jsonObject.put("status", 201);
            jsonObject.put("message", "File Uploaded sucessfully");
            long balance = updateTopUp(userProfile.getId(), token, fileId);
            jsonObject.put("current_balance", balance);
        }
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @Autowired
    private TopUpHistoryMapper topUpHistoryMapper;
    @Autowired
    UserBalanceMapper userBalanceMapper;

    private long updateTopUp(long user_id, String token, int fileId) {
        long balance = 0;
        TopUpHistory topUpHistory = topUpHistoryMapper.findLatestRecordByDateTokenAndUserId(user_id, token);
        if (topUpHistory != null) {
            UserBalance userBalance = userBalanceMapper.findByUserId(topUpHistory.getUser_id());
            if (userBalance == null) {
                balance = topUpHistory.getTopup_balance();
                UserBalance newUserBalance = new UserBalance();
                newUserBalance.setUser_id(topUpHistory.getUser_id());
                newUserBalance.setBalance(topUpHistory.getTopup_balance());
                userBalanceMapper.insert(newUserBalance);
            } else {
                long actualBalance = userBalance.getBalance();
                long currentBalance = topUpHistory.getTopup_balance() + actualBalance;
                userBalanceMapper.updateUserBalance(currentBalance, userBalance.getUser_id());
                balance = currentBalance;
            }
            topUpHistory.setFile_upload_id(fileId);
            topUpHistory.setStatus(1);
            topUpHistoryMapper.update(topUpHistory);
        }
        return balance;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

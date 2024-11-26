package com.example.movie.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/upload")
@Controller
public class UploadController {

    @Value("${com.example.movie.upload.path}")
    private String uploadPath;

    @GetMapping("/upload")
    public void getUpload() {
        log.info("get 파일 업로드 페이지 요청");
    }

    @PostMapping("/upload")
    public void postFileUpload(MultipartFile[] uploadFiles) {
        for (MultipartFile multipartFile : uploadFiles) {
            log.info("original file name : {}", multipartFile.getOriginalFilename());
            log.info("size : {}", multipartFile.getSize());
            log.info("content type : {}", multipartFile.getContentType());

            // 이미지 파일 여부 확인
            if (!multipartFile.getContentType().startsWith("image")) {
                return;
            }

            // 사용자가 올린 파일명
            String originName = multipartFile.getOriginalFilename();

            String saveFolderPath = makeFolder();

            // 파일 저장 - uuid(중복 해결)
            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + originName;

            Path savePath = Paths.get(saveName);

            try {
                // 폴더 저장
                multipartFile.transferTo(savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String makeFolder() {
        // 오늘 날짜
        LocalDate today = LocalDate.now();
        log.info(today);
        String dateStr = today.format(DateTimeFormatter.ofPattern("YYYY/MM/dd"));

        File dirs = new File(uploadPath, dateStr);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }

        // 날짜/시간/숫자에 특정 포맷 지정
        // SimpleDateFormat sdf = new SimpleDateFormat("YYYY/mm/dd");
        // sdf.format(new Date());

        return dateStr;
    }

}

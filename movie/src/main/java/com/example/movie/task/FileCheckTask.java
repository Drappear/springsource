package com.example.movie.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.movie.dto.MovieImageDTO;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FileCheckTask {
    @Autowired
    private MovieImageRepository movieImageRepository;

    @Value("${com.example.movie.upload.path}")
    private String uploadPath;

    private String getYesterdayFolder() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String result = yesterday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return result.replace("-", File.separator);
    }

    // second, minute, hour, day of month, month, day of week
    @Scheduled(cron = "0 0 0 * * *") // at 00:00, 매일 00시마다
    public void checkFile() {
        log.info("file check 메소드 실행");

        // db에서 전일 이미지 파일 목록 추출
        List<MovieImage> oldMovieImages = movieImageRepository.findOldFileAll();
        // entity -> dto
        List<MovieImageDTO> movieImageDTOs = oldMovieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        // 일반 이미지 파일명
        List<Path> filePaths = movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), dto.getUuid() + "_" + dto.getImgName()))
                .collect(Collectors.toList());

        // 썸네일 이미지 파일명
        movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), "s_" + dto.getUuid() + "_" + dto.getImgName()))
                .forEach(p -> filePaths.add(p));

        // 어제 날짜 파일 목록 추출
        File targetDir = Paths.get(uploadPath, getYesterdayFolder()).toFile();
        File[] removFiles = targetDir.listFiles(f -> filePaths.contains(f.toPath()) == false);

        // 비교 후 db목록과 일치하지 않는 파일 제거
        for (File file : removFiles) {
            log.info("remove file {}", file.getAbsolutePath());
            file.delete();
        }
    }
}

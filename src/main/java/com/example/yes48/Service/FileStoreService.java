package com.example.yes48.Service;

import com.example.yes48.domain.FileStore;
import com.example.yes48.repository.FileStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileStoreService {

    @Autowired
    private final FileStoreRepository fileStoreRepository;

    /**
     * 파일 등록
     */
    @Transactional
    public FileStore save(MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        String originalFilename = file.getOriginalFilename();
        String storeFileName = createdStoreFileName(originalFilename);

        File saveFile = new File(projectPath, storeFileName);
        file.transferTo(saveFile);

        FileStore saveFileStore = FileStore.builder()
                .filename(storeFileName)
                .filepath("/files/" + storeFileName)
                .build();

        return saveFileStore;
    }

    // 서버에 저장되는 파일명
    private String createdStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    // 파일 확장자명 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

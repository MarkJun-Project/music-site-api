package com.music.core.service.board;

import com.music.common.code.MusicCategory;
import org.springframework.web.multipart.MultipartFile;

public interface BoardAppService {
    void create(Long userId, String title, String description, String songTitle, MusicCategory category, MultipartFile multipartFile);
}

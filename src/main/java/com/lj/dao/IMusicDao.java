package com.lj.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by JuN on 17/1/24.
 */
public interface IMusicDao {
    public abstract ResponseEntity<byte []> downloadFile(String fileName);

    public abstract String uploadFile(MultipartFile file,String fileName);

//    public abstract String uploadFile(MultipartFile file,String fileName,String callback);

    public abstract String uploadFiles(MultipartFile[] files,String[] fileNames);

//    public abstract String uploadFiles(MultipartFile[] files,String[] fileNames,String callback);

//    public abstract String listAllMusic(String callback);

    public abstract String listAllMusic();
}

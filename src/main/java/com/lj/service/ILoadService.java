package com.lj.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by JuN on 17/1/25.
 */
public interface ILoadService {

    public abstract ResponseEntity<byte []> download(String fileName);

    public abstract String upload(MultipartFile file,String fileName);

//    public abstract String upload(MultipartFile file,String fileName,String callback);

    public abstract String uploads(MultipartFile[] files,String[] fileNames);

//    public abstract String uploads(MultipartFile[] files,String[] fileNames,String callback);
}

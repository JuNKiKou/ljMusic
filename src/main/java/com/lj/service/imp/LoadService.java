package com.lj.service.imp;/**
 * Created by JuN on 17/1/25.
 */

import com.lj.dao.IMusicDao;
import com.lj.service.ILoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * load service for project include upload and download
 *
 * @authorJuN
 * @create2017-01-25 15:53
 */
@Service
public class LoadService implements ILoadService{

    @Autowired
    private IMusicDao dao ;

    public ResponseEntity<byte[]> download(String fileName) {
        return dao.downloadFile(fileName);
    }

    public String upload(MultipartFile file, String fileName) {
        return dao.uploadFile(file,fileName);
    }

    public String uploads(MultipartFile[] files, String[] fileNames) {
        return dao.uploadFiles(files,fileNames);
    }

//    public String upload(MultipartFile file, String fileName, String callback) {
//        return dao.uploadFile(file,fileName,callback);
//    }

//    public String uploads(MultipartFile[] files, String[] fileNames, String callback) {
//        return dao.uploadFiles(files,fileNames,callback);
//    }
}

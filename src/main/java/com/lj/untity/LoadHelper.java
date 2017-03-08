package com.lj.untity;/**
 * Created by JuN on 17/1/25.
 */

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * upload and download helper class
 *
 * @authorJuN
 * @create2017-01-25 14:54
 */
public class LoadHelper {

    public static ResponseEntity<byte []> download(String fileName, File file){

        String fName = "";
        try {
            fName = new String(fileName.getBytes("UTF-8"),"iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",fName);
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String upload(MultipartFile file, String filePath){
        judgeDir();
        if (!file.isEmpty()){
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                System.out.println("文件保存失败");
                e.printStackTrace();
                return CommonUntity.EXCEPTION;
            }

            return CommonUntity.SUCCESS;
        }
        return CommonUntity.FILE_NOT_EXIST;
    }

    public static String uploads(MultipartFile[] files, String[] filePaths){
        boolean FILES_ARE_ALL_NULL = true;
        judgeDir();
        for (int i = 0 ;i < files.length ;i++){
            if (!files[i].isEmpty()){
                FILES_ARE_ALL_NULL = false;
                try {
                    files[i].transferTo(new File(filePaths[i]));
                } catch (IOException e) {
                    e.printStackTrace();
                    return CommonUntity.EXCEPTION;
                }
            }
        }
        if (FILES_ARE_ALL_NULL){
            return CommonUntity.FILE_ARE_ALL_NULL;
        }else {
            return CommonUntity.SUCCESS;
        }
    }

    private static void judgeDir(){
        File dirPath = new File(PathUntity.MUSIC_SAVE_ADDRESS);
        if (!dirPath.exists()){
            dirPath.mkdir();
        }
    }
}

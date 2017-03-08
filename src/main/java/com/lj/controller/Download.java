package com.lj.controller;/**
 * Created by JuN on 17/1/24.
 */

import com.lj.service.ILoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * download file and files controller
 *
 * @authorJuN
 * @create2017-01-24 22:52
 */
@Controller
@RequestMapping("/download")
public class Download {

    @Autowired
    private ILoadService service ;

    @RequestMapping(value = "/downloadMusic",method = RequestMethod.POST)
    public ResponseEntity<byte []> downloadMusic(@RequestParam("musicName") String musicName){
        return service.download(musicName);
    }

}

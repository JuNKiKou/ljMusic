package com.lj.controller;/**
 * Created by JuN on 17/1/24.
 */

import com.lj.service.ILoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * upload file and files controller
 *
 * @authorJuN
 * @create2017-01-24 22:55
 */
@Controller
@RequestMapping("/upload")
public class Upload {

    @Autowired
    private ILoadService service;

    @ResponseBody
    @RequestMapping(value = "/uploadMusic",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String uploadMusic(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, HttpServletRequest request,HttpServletResponse response){
//        String requestType = request.getHeader("x-request-with");
//        if (requestType == null){
//            return service.upload(file,fileName);
//        }
//        String callbackName = request.getParameter("callback");
//        response.setHeader("Access-Control-Allow-Origin","*");
        return service.upload(file,fileName);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadMusics",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String uploadMusics(@RequestParam("files") MultipartFile[] files, @RequestParam("fileNames") String[] fileNames, HttpServletRequest request, HttpServletResponse response){
//        String requestType = request.getHeader("x-request-with");
//        if (requestType == null){
//            return service.uploads(files,fileNames);
//        }
//        String callbackName = request.getParameter("callback");
//        response.setHeader("Access-Control-Allow-Origin","*");
        return service.uploads(files,fileNames);
    }
}

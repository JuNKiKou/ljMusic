package com.lj.controller;/**
 * Created by JuN on 17/1/27.
 */

import com.lj.service.IListMusicService;
import com.lj.service.imp.ListMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * list music controller
 *
 * @authorJuN
 * @create2017-01-27 14:41
 */
@Controller
@RequestMapping("/listMusic")
public class ListMusic {

    @Autowired
    private IListMusicService service ;

    @ResponseBody
    @RequestMapping(value = "/listAllMusic",produces = "text/html;charset=UTF-8",method = RequestMethod.GET)
    public String listAllMusic(HttpServletRequest request, HttpServletResponse response){
//        String requestType = request.getHeader("x-request-with");
//        if (requestType == null){
//            //传统请求
//            return service.listAllMusic();
//        }
//        String callbackName = request.getParameter("callback");
//        response.setHeader("Access-Control-Allow-Origin","*");
        return service.listAllMusic();
    }
}

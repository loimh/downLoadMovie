package com.movie.download.Controller;

import com.movie.download.service.DownLoadMovieService;
import com.movie.download.serviceImpl.DownLoadMovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {

    @Autowired
    DownLoadMovieServiceImpl downLoadMovieService;
    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello(@RequestParam String downLoadPath, @RequestParam String movieUrl){
        downLoadMovieService.downLoadMovie("H:\\movie","https://v.qq.com/x/cover/uwr8gse5tajsfgj/g0031lpsten.html");
//        downLoadMovieService.downLoadMovie(downLoadPath,movieUrl);
        return "nihao";
    }

}

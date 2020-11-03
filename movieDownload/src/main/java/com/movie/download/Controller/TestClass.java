package com.movie.download.Controller;

import com.movie.download.service.DownLoadMovieService;
import com.movie.download.serviceImpl.DownLoadMovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {

    @Autowired
    DownLoadMovieServiceImpl downLoadMovieService;
    @RequestMapping("hello")
    public String hello(){
        downLoadMovieService.downLoadMovie();
        return "nihao";
    }

}

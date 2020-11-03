package com.movie.download.serviceImpl;

import com.movie.download.service.DownLoadMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class DownLoadMovieServiceImpl implements DownLoadMovieService {

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private static Logger LOGGER = LoggerFactory.getLogger(DownLoadMovieServiceImpl.class);
    @Override
    public void downLoadMovie(String downLoadPath,String movieUrl) {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
             runShell(downLoadPath,movieUrl);
            }
        });
}

    //脚本参数:1.applicationName:应用名称 2.dir:源码路径 3.bundle identifier 4.打包版本号:version 5.流水版本号:versionCode 6.App名字 7.创建安装包的时间 8.服务器地址 9.appkey 10.描述文件地址
    public static void runShell(String downLoadPath,String movieUrl){
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;
        Process process = null;
        String[] command = new String[]{"./src/main/java/com/movie/download/serviceImpl/downLoadMovie.bat",downLoadPath,movieUrl};
        try {

            process = Runtime.getRuntime().exec(command);

            CommandStreamGobbler errorGobbler = new CommandStreamGobbler(process.getErrorStream(), command, "ERR");
            CommandStreamGobbler outputGobbler = new CommandStreamGobbler(process.getInputStream(), command, "STD");


            errorGobbler.start();
            // 必须先等待错误输出ready再建立标准输出
            while (!errorGobbler.isReady()) {
                Thread.sleep(10);
            }
            outputGobbler.start();
            while (!outputGobbler.isReady()) {
                Thread.sleep(10);
            }
            int exitValue = process.waitFor();
            if (0 != exitValue) {
               LOGGER.info("下载失败");
            }else{
                LOGGER.info("下载成功");
            }

        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage(),e);
        } finally {
            try {
                if (stdISR != null) {
                    stdISR.close();
                }
                if (errISR != null) {
                    errISR.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                System.out.println("正式执行命令：" + command + "有IO异常");
            }
        }
    }

}

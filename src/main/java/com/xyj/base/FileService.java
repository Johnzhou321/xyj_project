package com.xyj.base;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件本地服务器读写服务类
 * @author zhouguangming
 * @date 6/17/19
 * @since
 */
@Service
public class FileService {

    private static String targetPath = "/home/user/upload/";

    /**
     * 保存文件
     * @param file
     * @return String
     * @author zhouguangming
     * @date 6/17/19
     * @since
     */
    public String save(MultipartFile file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        File targeFile = new File(targetPath + today);
        //如果文件目录不存在，就执行创建
        if(!targeFile.isDirectory()){
            targeFile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            //创建目标文件
            File targetFile = new File(targeFile + file.getOriginalFilename());
            fos = new FileOutputStream(targetFile);
            //写入目标文件
            InputStream inputStream = file.getInputStream();
            byte[] buffer = new byte[1024*1024];
            int byteRead = 0;
            while((byteRead=inputStream.read(buffer))!=-1){
                fos.write(buffer, 0, byteRead);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

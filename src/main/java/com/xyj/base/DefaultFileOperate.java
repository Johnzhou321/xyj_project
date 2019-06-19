package com.xyj.base;

import com.xyj.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作默认实现类
 * @author zhouguangming
 * @date 6/19/19
 * @since
 */
@Component
public class DefaultFileOperate extends AbstractFileOperate{

    private static String fileSystem = PropertyUtil.getProperty("application.properties", "file.system");
    @Autowired
    private GridfsService gridfsService;

    @Override
    public <T> T save(MultipartFile file) {
        if (fileSystem.equals("system")){
            return (T)FileSystemUtil.save(file);
        }else if (fileSystem.equals("FastDFS")){
            return (T)FastDFSClient.save(file);
        }else {
            return (T)gridfsService.save(file);
        }
    }

}

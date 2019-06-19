package com.xyj.base;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作公用接口
 * @author zhouguangming
 * @date 6/19/19
 * @since
 */
public interface FileOperate {
    /**
     * 保存文件
     * @param file
     * @return
     */
    <T> T save(MultipartFile file);

    /**
     * 根据文件ID或者路径删除文件
     * @param fileId
     */
    void deleteFileId(String fileId);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    <T> T getFileById(Object id);


}

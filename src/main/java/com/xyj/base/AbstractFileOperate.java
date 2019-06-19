package com.xyj.base;

import org.apache.poi.ss.formula.functions.T;

/**
 * 文件操作抽象类
 * @author zhouguangming
 * @date 6/19/19
 * @since
 */
public abstract class AbstractFileOperate implements FileOperate{
    @Override
    public void deleteFileId(String fileId){

    }

    @Override
    public <T> T getFileById(Object id){
        return null;
    }

}

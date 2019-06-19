package com.xyj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取资源文件工具类
 * @author zhouguangming
 * @date 6/19/19
 * @since
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    synchronized static private void loadProps(String propertiesName){
        props = getProperties(propertiesName);
    }

    public static Properties getProperties(String propertiesName){
        Properties props = new Properties();
        InputStream in = null;
        try {
            //        <!--通过类加载器进行获取properties文件流-->
            in = PropertyUtil.class.getClassLoader().getResourceAsStream(propertiesName);
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error(propertiesName + "common.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(propertiesName + "文件流关闭出现异常");
            }
        }
        return props;
    }

    public static String getProperty(String propertiesName, String key){
        if(null == props) {
            loadProps(propertiesName);
        }
        return props.getProperty(key);
    }

    public static String getProperty(String propertiesName, String key, String defaultValue) {
        if(null == props) {
            loadProps(propertiesName);
        }
        return props.getProperty(key, defaultValue);
    }
}


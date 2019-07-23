package com.xyj.base;

import com.xyj.core.editor.NullableCustomBooleanEditor;
import com.xyj.core.editor.NullableCustomIntegerEditor;
import com.xyj.core.editor.NullableCustomNumberEditor;
import com.xyj.core.editor.SqlDateEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;

/**
 * 基础控制类
 * @author zhouguangming
 * @date 7/22/19
 * @since
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // 日期解析
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(dateFormat, true));
        binder.registerCustomEditor(java.util.Date.class, new SqlDateEditor(dateFormat, true));

        // 数值解析
        binder.registerCustomEditor(double.class, new NullableCustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(int.class, new NullableCustomIntegerEditor(Integer.class, true));
        binder.registerCustomEditor(long.class, new NullableCustomIntegerEditor(Long.class, true));

        // boolean解析
        binder.registerCustomEditor(boolean.class, new NullableCustomBooleanEditor(true));
    }
}

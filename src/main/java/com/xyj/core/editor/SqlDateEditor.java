package com.xyj.core.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Created by song on 21/09/2017.
 * <p>
 * 解析请求参数中的日期串，转换成java.sql.Date对象
 */
public class SqlDateEditor extends PropertyEditorSupport
{
    private DateFormat dateFormat;
    private final boolean allowEmpty;

    public SqlDateEditor(DateFormat dateFormat, boolean allowEmpty)
    {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (allowEmpty && !StringUtils.hasText(text))
        {
            // Treat empty String as null value.
            setValue(null);
        }
        else
        {
            try
            {
                setValue(new Date(this.dateFormat.parse(text).getTime()));
            }
            catch (ParseException ex)
            {
                throw new IllegalArgumentException("无法解析日期：" + text + ", 支持格式如：2017-1-1");
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText()
    {
        //java.util.Date value = (java.util.Date) getValue();
        Date value = (Date) getValue();
        return (value != null ? this.dateFormat.format(new java.util.Date(value.getTime())) : "");
    }
}
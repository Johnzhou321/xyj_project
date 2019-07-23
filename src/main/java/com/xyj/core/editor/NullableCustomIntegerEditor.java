package com.xyj.core.editor;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * 当接收到空串时自动转换为数值0
 *
 * Created by song on 13/09/2017.
 */
public class NullableCustomIntegerEditor extends CustomNumberEditor
{

    public NullableCustomIntegerEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException
    {
        super(numberClass, allowEmpty);
    }


    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (text == null || text.trim().equals(""))
        {
            setValue(0);
        }
        else
        {
            super.setAsText(text);
        }
    }
}

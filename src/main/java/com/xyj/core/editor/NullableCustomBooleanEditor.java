package com.xyj.core.editor;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 * 当接收到空串时自动转换为数值0.00
 *
 * Created by song on 13/09/2017.
 */
public class NullableCustomBooleanEditor extends CustomBooleanEditor
{
    public NullableCustomBooleanEditor(boolean allowEmpty)
    {
        super(allowEmpty);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (text == null || text.trim().equals(""))
        {
            setValue(false);
        }
        else
        {
            super.setAsText(text);
        }
    }
}

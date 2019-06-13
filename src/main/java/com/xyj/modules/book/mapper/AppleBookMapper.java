package com.xyj.modules.book.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.book.model.AppleBook;

import java.util.List;


public interface AppleBookMapper extends MyMapper<AppleBook> {

    List<AppleBook> selectByBookName(String name);
}
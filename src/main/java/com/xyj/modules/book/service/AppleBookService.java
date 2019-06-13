package com.xyj.modules.book.service;

import com.xyj.modules.book.model.AppleBook;
import com.xyj.modules.sys.model.SysUser;

import java.util.List;

public interface AppleBookService {
    List<AppleBook> getAll(AppleBook appleBook, String keyword);

    void borrow(Integer bookId, SysUser sysUser);

    AppleBook getBookById(Integer bookId);
}

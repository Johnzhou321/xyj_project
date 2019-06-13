package com.xyj.modules.book.vo;

import com.xyj.modules.book.model.AppleBorrow;

public class BorrowHistoryVo extends AppleBorrow{
    private String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}

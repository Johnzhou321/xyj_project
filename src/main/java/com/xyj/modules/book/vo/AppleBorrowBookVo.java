package com.xyj.modules.book.vo;

import com.xyj.modules.book.model.AppleBook;
import com.xyj.modules.book.model.AppleBorrow;

public class AppleBorrowBookVo extends AppleBorrow{
    private AppleBook book;


    public AppleBook getBook() {
        return book;
    }

    public void setBook(AppleBook book) {
        this.book = book;
    }

}

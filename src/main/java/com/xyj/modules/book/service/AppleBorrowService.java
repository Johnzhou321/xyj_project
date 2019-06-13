package com.xyj.modules.book.service;

import com.xyj.modules.book.model.AppleBorrow;
import com.xyj.modules.book.vo.AppleBorrowBookVo;
import com.xyj.modules.book.vo.BorrowHistoryVo;
import com.xyj.modules.sys.model.SysUser;

import java.util.List;

public interface AppleBorrowService {
    List<AppleBorrowBookVo> getBorrowList(AppleBorrowBookVo appleBorrowBookVo, SysUser sysUser) throws Exception;

    void continueBorrow(AppleBorrow appleBorrow, SysUser sysUserVo);

    void returnBook(AppleBorrow appleBorrow, SysUser sysUserVo);

    List<BorrowHistoryVo> selectBorrowHistoryList(Integer bookId, Integer id);

    List<BorrowHistoryVo> selectBorrowHistoryListByUser(Integer id);
}

package com.xyj.modules.sys.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.sys.model.SysDept;

import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {
    List<SysDept> ListTopDept();

    List<SysDept> getChildDeptList(Integer id);
}
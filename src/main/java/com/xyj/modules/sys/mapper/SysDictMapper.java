package com.xyj.modules.sys.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.sys.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysDictMapper extends MyMapper<SysDict> {
    SysDict getDictValueChild(@Param("param") Map<String, Object> param);
}
package com.xyj.modules.sys.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.sys.model.SysMenuRole;

public interface SysMenuRoleMapper extends MyMapper<SysMenuRole> {
    void deleteByRoleId(Integer roleId);
}
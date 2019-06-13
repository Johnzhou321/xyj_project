package com.xyj.modules.sys.service;




import com.xyj.modules.sys.model.SysRole;

import java.util.List;

public interface RoleService {

    List<SysRole> findByUserId(Integer id);

    List<SysRole> getAll(SysRole sysRole, String keyword);

    void batchDelete(Integer[] ids);

    void saveOrUpdate(SysRole sysUser);

    SysRole getById(Integer id);

    void deleteById(Integer id);
}
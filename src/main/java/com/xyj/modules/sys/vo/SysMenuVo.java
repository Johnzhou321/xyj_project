package com.xyj.modules.sys.vo;

import com.xyj.modules.sys.model.SysMenu;

import java.util.List;

public class SysMenuVo extends SysMenu {
    private List<SysMenuVo> children;

    public List<SysMenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuVo> children) {
        this.children = children;
    }
}

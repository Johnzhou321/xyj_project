package com.xyj.modules.job.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.job.model.SysTask;

public interface SysTaskMapper extends MyMapper<SysTask> {
    int batchRemove(Long[] ids);
}
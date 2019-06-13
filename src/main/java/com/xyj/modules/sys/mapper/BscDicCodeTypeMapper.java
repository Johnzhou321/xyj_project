package com.xyj.modules.sys.mapper;

import com.xyj.core.mapper.MyMapper;
import com.xyj.modules.sys.model.BscDicCodeType;

public interface BscDicCodeTypeMapper extends MyMapper<BscDicCodeType> {
    int myInsertUseGeneratedKeys(BscDicCodeType bscDicCodeType);
}
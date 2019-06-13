package com.xyj.modules.juhe.stock.service;

import com.xyj.modules.juhe.stock.vo.StockResult;

public interface StockService {
    StockResult getStockListByType(Integer page, String type);
}

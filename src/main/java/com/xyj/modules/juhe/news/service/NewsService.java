package com.xyj.modules.juhe.news.service;

import net.sf.json.JSONObject;

public interface NewsService {
    public JSONObject getNewsList(String keyword);
}

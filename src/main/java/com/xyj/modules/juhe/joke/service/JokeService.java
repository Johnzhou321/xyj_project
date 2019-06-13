package com.xyj.modules.juhe.joke.service;

import net.sf.json.JSONObject;

public interface JokeService {
    JSONObject getRecentJokeList(Integer page, Integer pagesize);
}

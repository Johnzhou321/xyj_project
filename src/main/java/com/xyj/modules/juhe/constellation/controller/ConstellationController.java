package com.xyj.modules.juhe.constellation.controller;

import com.xyj.modules.juhe.constellation.service.ConstellationService;
import com.xyj.modules.sys.model.SysUser;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/constellation")
public class ConstellationController {

    @Autowired
    private ConstellationService constellationService;

    @RequestMapping("/getInfoByConstellation")
    public JSONObject getInfoByConstellation(HttpServletRequest request){
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        if(user!=null){
            String constellation=user.getConstellation();
            if(StringUtils.isNotBlank(constellation)){
                return constellationService.getInfoByConstellation(constellation);
            }
        }
        return null;
    }
}

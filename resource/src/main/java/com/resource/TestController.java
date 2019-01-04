package com.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Gu danpeng
 * @date: 2019-1-4
 * @version：1.0
 */
@Controller
public class TestController {

    @GetMapping("test")
    @ResponseBody
    public String getResource(){
        return "Here are 5 Apples";
    }

}

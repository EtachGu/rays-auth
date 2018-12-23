package com.client1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Gu danpeng
 * @date: 2018-12-22
 * @version：1.0
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    @ResponseBody
    public String home(){
        return "This is Home";
    }
}

package com.client1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/loginClient1")
    public @ResponseBody String login(@RequestParam(value = "code",required = false) String code){
        return "<p>loginClient1</p>";
    }
}

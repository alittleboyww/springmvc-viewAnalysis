package com.liu.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
public class UserController{

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String printHello(ModelMap modelMap){
        modelMap.addAttribute("message","helloworld");
        System.out.println("默认配置");
        return "hello";
    }
    @RequestMapping(value = "/liu",method = RequestMethod.GET)
    public String printLiu(Model model){
        model.addAttribute("message","liu hello");
        System.out.println("xml配置");
        return "liu";
    }

    @RequestMapping(value = "/jia",method = RequestMethod.GET)
    public ModelAndView printJia(ModelAndView modelAndView){
        modelAndView.addObject("message","hello jia");
        modelAndView.setViewName("jia");
        System.out.println("资源绑定");
        return modelAndView;
    }
}

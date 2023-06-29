package com.socialvideo.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@EnableAutoConfiguration
public class ErrorController {

	 public ErrorController() {
    }

    @RequestMapping( value = "/error/404.html" )
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("error-notfound-index");
        mav.addObject("exception", e);  
        return mav;
    }
    
    
    @RequestMapping( value = "/error/403.html" )
    public ModelAndView handleError403(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("error-accessdenied-index");
        mav.addObject("exception", e);  
        return mav;
    }
    
    
    @RequestMapping( value = "/error/500.html" )
    public ModelAndView handleError500(HttpServletRequest request, Exception e)   {
        ModelAndView mav = new ModelAndView("error-serviceunavailable-index");
        mav.addObject("exception", e);  
        return mav;
    }
    
    
    @RequestMapping(value = "/error/message", method = RequestMethod.GET)
    public ModelAndView handleMessageError(@ModelAttribute("message") String message)   {
        ModelAndView mav = new ModelAndView("error-message-index");
        mav.addObject("message",message);  
        return mav;
    }
     
    
}
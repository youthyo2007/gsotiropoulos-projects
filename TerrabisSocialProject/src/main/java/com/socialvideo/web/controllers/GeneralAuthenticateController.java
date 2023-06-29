
package com.socialvideo.web.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.socialvideo.constant.WebConstants;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/authenticate")
public class GeneralAuthenticateController {

	@RequestMapping(value="/exception/{message}", method=RequestMethod.GET)
	public ModelAndView authenticate(@PathVariable String message) {
		ModelAndView mav = new ModelAndView("authenticate-exception-view");
		mav.addObject(WebConstants.EXCEPTION, message);
		return mav; 
		
	}

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public ModelAndView showLoginView(Model model) {
		ModelAndView mav = new ModelAndView("authenticate-signin-view");
		model.addAttribute(WebConstants.NAVBAR_SHOWSIGNBUTTON, new Boolean(false));
		
		return mav; 
		
	}
	
	

}

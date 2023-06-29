package com.socialvideo.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.socialvideo.web.exceptions.VideoSecurityException;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    
    @ExceptionHandler(RateLimitExceededException.class)
	public String rateLimitExceeded(RateLimitExceededException e, Model model) {
		model.addAttribute("providerId", e.getProviderId());
		return "ratelimit";
	}
    
        
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e) {
        return e.getMessage();
    }
    
    @ExceptionHandler(AccessDeniedException.class)
	  public ModelAndView myAccessDeniedError(Exception exception) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("error-accessdenied-index");
	    mav.addObject("exception", exception);
	    return mav;
	}
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
            ModelAndView mav = new ModelAndView("error-notfound-index");
            mav.addObject("exception", e);  
            return mav;
    }

    @ExceptionHandler(Exception.class)
	  public ModelAndView myGeneralError(Exception exception) {
	    ModelAndView mav = new ModelAndView("error-general-index");
	    mav.addObject("exception", exception);
	    exception.printStackTrace();
	    return mav;
	}
    
     @ExceptionHandler(VideoSecurityException.class)
	  public String videoSecurity(VideoSecurityException exception) {
	    return exception.getRedirect();
	}
  
    
    
    
    
    
    
    

}
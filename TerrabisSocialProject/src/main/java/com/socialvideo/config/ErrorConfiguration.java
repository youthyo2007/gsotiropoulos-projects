package com.socialvideo.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfiguration implements EmbeddedServletContainerCustomizer {

  @Override public void customize( ConfigurableEmbeddedServletContainer container) {
      container.addErrorPages( new ErrorPage( HttpStatus.NOT_FOUND, "/error/404.html" ) );
      container.addErrorPages( new ErrorPage( HttpStatus.FORBIDDEN, "/error/403.html" ) );
      container.addErrorPages( new ErrorPage( HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html" ) );
  }
  
}
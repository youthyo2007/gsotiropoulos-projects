package com.socialvideo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.socialvideo.data.services.CurrentSocialUserDetailsService;
import com.socialvideo.data.services.CurrentUserDetailsService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.restsecurity.RESTAuthenticationEntryPoint;
import com.socialvideo.restsecurity.RESTAuthenticationFailureHandler;
import com.socialvideo.restsecurity.RESTAuthenticationSuccessHandler;
import com.socialvideo.validation.EmailValidator;
import com.socialvideo.validation.PasswordMatchesValidator;


/*@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)*/
//@ComponentScan(basePackages = { "com.socialvideo.security" })


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecSecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    
    
    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	
	
	@Autowired
    private  UserService userService;
    
    @Autowired
    private CurrentUserDetailsService currentUserDetailsService;

	
	
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }
 
    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new CurrentSocialUserDetailsService();
    }
    
    
/*    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
                .password("admin").roles("ADMIN");
        builder.authenticationProvider(authProvider());
    }
 */
    
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    

    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(currentUserDetailsService).passwordEncoder(passwordEncoder());
	}
    
    
    
/*    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
    */

    
    
    @SuppressWarnings("deprecation")
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	 //http.csrf().disable();
    	 http.headers().frameOptions().disable();
    	 http.sessionManagement()
         .maximumSessions(1)
         .expiredUrl("/authenticate/exception/sessionexpirederror")
         .maxSessionsPreventsLogin(false)
           //.sessionRegistry(sessionRegistry())
          .and()
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          //.invalidSessionUrl("/")
    	  .sessionFixation().migrateSession();
    	  http.authorizeRequests().antMatchers("/").permitAll()
    	 .antMatchers("/admin1/**").hasAuthority("ROLEADMIN")
    	 .antMatchers("/statistics1/**").hasAuthority("ROLEADMIN")
    	 .antMatchers("/tool/youtube/**").hasAuthority("ROLEYOUTUBEIMPORTTOOL")
    	 .antMatchers("/upload/**").authenticated()
    	 .antMatchers("/video-organizer/**").authenticated().and()
    	 //.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
         .formLogin().successHandler(authenticationSuccessHandler)
         .loginPage("//authenticate/signin").permitAll().loginProcessingUrl("/login").permitAll()
         .failureHandler(authenticationFailureHandler)
         .usernameParameter("email")
         .permitAll().and()
         .logout()
         .logoutSuccessUrl("/video/map/normal/_")
         .deleteCookies("remember-me")
         //.deleteCookies("remember-me", "JSESSIONID")
         .permitAll()
         .and()
         .rememberMe()
         .and()
         .apply(new SpringSocialConfigurer()
                .postLoginUrl("/video/map/normal/_")
                .defaultFailureUrl("/authenticate/exception/socialconnecterror")
                .alwaysUsePostLoginUrl(true));
    }

    


   // @SuppressWarnings("deprecation")
	//@Override
    protected void configureWorking(HttpSecurity http) throws Exception {
    	//http.csrf().disable();
    	 http.headers().frameOptions().disable();
    	
    	 http.sessionManagement()
          .maximumSessions(1)
          .expiredUrl("/authenticate/exception/sessionexpirederror")
         .maxSessionsPreventsLogin(false)
         //.sessionRegistry(sessionRegistry())
         .and()
         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
         //.invalidSessionUrl("/")
    	 .sessionFixation().migrateSession();
    	 http.authorizeRequests().antMatchers("/").permitAll()
    	 .antMatchers("/admin/**").hasAuthority("ROLEADMIN")
    	 .antMatchers("/video-organizer/**").authenticated().and()
    	//.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
        .formLogin().successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .usernameParameter("email")
        .permitAll().and()
        .logout()
        .logoutSuccessUrl("/video/map/normal/_")
        .deleteCookies("remember-me")
        //.deleteCookies("remember-me", "JSESSIONID")
        .permitAll()
        .and()
        .rememberMe()
        .and()
        .apply(new SpringSocialConfigurer()
                .postLoginUrl("/video/map/normal/_")
                .defaultFailureUrl("/authenticate/exception/socialconnecterror")
                .alwaysUsePostLoginUrl(true));
    }
    
	}


    
    /*
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	   http.authorizeRequests()
           .antMatchers("/", "/public/**").permitAll()
           .and()
           .formLogin()
           .successHandler(authenticationSuccessHandler)
           .failureHandler(authenticationFailureHandler)
           .failureUrl("/login?error")
           .usernameParameter("email")
           .permitAll()
           .and()
           .logout()
           .logoutUrl("/logout")
           .deleteCookies("remember-me")
           .logoutSuccessUrl("/")
           .permitAll()
           .and()
           .rememberMe();
    }
   */ 


/*
@Configuration
@EnableWebMvcSecurity
public class SecSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/public/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe();
    }
    
    
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	  auth.authenticationProvider(authProvider());
    }
    
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
/*
    @Override
    protected void configure( AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
*/    
   /* @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
    	//httpSecurity.csrf().disable();
    	httpSecurity.authorizeRequests().antMatchers("/").permitAll();
    	
    	httpSecurity
         .formLogin().failureUrl("/login?error=true")
         .defaultSuccessUrl("/")
         .loginPage("/login")
         .successHandler(myAuthenticationSuccessHandler)
         .usernameParameter("email")
         .passwordParameter("password")
         .permitAll()
         .and()
         .logout()
         .invalidateHttpSession(false)
         .logoutUrl("/logout")
         .deleteCookies("remember-me")
         .logoutSuccessUrl("/")
         .permitAll()
         .and()
         .rememberMe();
   
}
    */

    /*
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        
    */

/*    @Override
    public void configure( WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
    */
    

    
    	 
/*    	
    	httpSecurity.authorizeRequests().antMatchers("/invalid-session*").anonymous()
         .anyRequest().authenticated()
         .and()
     .formLogin()
         .loginPage("/signin")
         .loginProcessingUrl("/j_spring_security_check")
         .defaultSuccessUrl("user-2946946/")
         .failureUrl("/signin?error=true")
         .successHandler(myAuthenticationSuccessHandler)
         .usernameParameter("j_username")
         .passwordParameter("j_password")
     .permitAll()
         .and()
     .sessionManagement()
         .invalidSessionUrl("/invalid-session")
         .sessionFixation().none()
     .and()
     .logout()
         .invalidateHttpSession(false)
         .logoutUrl("/j_spring_security_logout")
         .logoutSuccessUrl("/logout?success=true")
         .deleteCookies("JSESSIONID")
         .permitAll();
         
         
}         
    	*/
    	
    	
    
    
   
    

    

    //@Override
    /*protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/","/j_spring_security_check*","/login*", "/logout*", "/signin/**", "/signup/**",
                        "/user/registration-confirm*", "/user/registration-submit*","/user/registration-success*","/user/registration-resendtoken*")
                .permitAll()
                .antMatchers("/invalid-session*").anonymous()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("user-2946946/")
                .failureUrl("/signin?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .usernameParameter("j_username")
                .passwordParameter("j_password")
            .permitAll()
                .and()
            .sessionManagement()
                .invalidSessionUrl("/invalid-session")
                .sessionFixation().none()
            .and()
            .logout()
                .invalidateHttpSession(false)
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/logout?success=true")
                .deleteCookies("JSESSIONID")
                .permitAll();
     // @formatter:on
    }
*/
    // beans

   /* @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
*/
	/*public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;

	
	}
	}
*/

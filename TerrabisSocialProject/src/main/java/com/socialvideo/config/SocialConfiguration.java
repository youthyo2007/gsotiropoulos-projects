package com.socialvideo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.socialvideo.data.services.AccountConnectionSignUpService;




@Configuration
@EnableSocial
public class SocialConfiguration extends SocialConfigurerAdapter {

	
	 @Autowired
	 private AccountConnectionSignUpService accountConnectionSignUpService;
	
	@Autowired
    private DataSource dataSource;
	
/*    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(
            env.getProperty("spring.social.facebook.appId"),
            env.getProperty("spring.social.facebook.appSecret")));
    }
    */
    
    @Override
	public UserIdSource getUserIdSource() {
    	return new AuthenticationNameUserIdSource();
	}

    /*@Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText()
        );
    }*/
    
    @Override
    public UsersConnectionRepository
    getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
     JdbcUsersConnectionRepository repository =
      new JdbcUsersConnectionRepository(dataSource, cfl, Encryptors.noOpText());
     repository.setConnectionSignUp(accountConnectionSignUpService);
     return repository;
    }
   }
    
    

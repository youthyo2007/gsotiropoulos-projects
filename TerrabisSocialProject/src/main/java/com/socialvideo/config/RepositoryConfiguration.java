package com.socialvideo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.socialvideo.data"})
@EntityScan(basePackages = {"com.socialvideo.data.model"})
@EnableJpaRepositories(basePackages = {"com.socialvideo.data.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
	
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.primjpa")
    public DataSource getDataSource() {
       BasicDataSource dataSource = new BasicDataSource();
       return dataSource;
   }

    //dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    //dataSource.setUrl("jdbc:mysql://localhost:3306/socialvideo?user=root&password=root");
    //dataSource.setUrl("jdbc:mysql://aa15f0c9jcvbclc.cu0ort3e8if3.eu-west-1.rds.amazonaws.com:3306/socialvideo?user=root&password=password1!");
	
	
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[] { "com.socialvideo.data.model" });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

	
	 Properties additionalProperties() {
	         Properties hibernateProperties = new Properties();
	        //hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
	        hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLInnoDBDialect");
	        return hibernateProperties;
	    }


    @Bean
    @Primary
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
	
	
	
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


	
}
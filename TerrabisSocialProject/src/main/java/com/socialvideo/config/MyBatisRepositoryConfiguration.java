package com.socialvideo.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
@Configuration
@MapperScan("com.socialvideo.data.mappers")
public class MyBatisRepositoryConfiguration {
	

		@Bean
		@ConfigurationProperties(prefix="spring.secondmybatis")
	    public DataSource getDataSource() {
	       BasicDataSource dataSource = new BasicDataSource();
	       return dataSource;
	   }
	
       //dataSource.setDriverClassName("com.mysql.jdbc.Driver");
       //dataSource.setUrl("jdbc:mysql://localhost:3306/socialvideo?user=root&password=root");
       //dataSource.setUrl("jdbc:mysql://aa15f0c9jcvbclc.cu0ort3e8if3.eu-west-1.rds.amazonaws.com:3306/socialvideo?user=root&password=password1!");

		
		
	   @Bean(name = "myBatisTransactionManager")
	   public DataSourceTransactionManager getTransactionManager() {
	       return new DataSourceTransactionManager(getDataSource());
	   }
	   
	   @Bean(name = "myBatisSqlSessionFactory")
	   public SqlSessionFactory sqlSessionFactory() throws Exception {
	      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	      sessionFactory.setDataSource(getDataSource());
	      return sessionFactory.getObject();
	   }
} 
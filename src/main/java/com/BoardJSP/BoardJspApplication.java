package com.BoardJSP;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@SpringBootApplication
public class BoardJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardJspApplication.class, args);
	}

	@Bean //이 메서드를 객체생성해줘야 한다.
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		Resource[] res = new PathMatchingResourcePatternResolver()
				.getResources("classpath:mybatis/mapper/*.xml");

		sessionFactory.setMapperLocations(res);


		return sessionFactory.getObject();
		//.xml 형태의 모든 파일을 받기때문에 배열로 받는다.

	}

}

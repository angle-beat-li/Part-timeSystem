package com.liy.parttimesystem;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class PartTimeSystemApplicationTests {

		//数据源
		@Autowired
		DataSource dataSource;
		//用于访问数据库的组件
		@Autowired
		JdbcTemplate jdbcTemplate;
		@Test
		void printData() throws SQLException {
			System.out.println("默认数据源为：" + dataSource.getClass());
			System.out.println("数据库连接实例：" + dataSource.getConnection());
			//访问数据库user表，查询user表的数据量
			Integer i = jdbcTemplate.queryForObject("SELECT count(*) from `user`", Integer.class);
			System.out.println("user 表中共有" + i + "条数据。");
		}


}

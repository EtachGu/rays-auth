package com.auth.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author: Gu danpeng
 * @date: 2019-1-2
 * @version：1.0
 */
@Configuration
@MapperScan(basePackages = "com.auth.mapper")
public class MySQLDataSourceConfiguration {

//    @Autowired
//    DataSource dataSource;

    //将properties中以mysql为前缀的参数值，写入方法返回的对象中
    //默认数据源
    @Bean
//    @Qualifier("mysql")
    @Primary
//    @ConfigurationProperties(prefix="mysql.datasource")
    public DataSource mysqDataSource() {
        //通过DataSourceBuilder构建数据源
//        DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        System.out.println("MySQL Datasource: " + dataSource);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }
}

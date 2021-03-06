package com.conqueror.blacklist.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
* @author: Sven
* @E-mail: 1050676672@qq.com
* @version: 创建时间：2018年3月30日 下午2:43:11
* @Description: TODO
*/
@Configuration
public class CommonDruidConfig {
//	  private Logger logger = LoggerFactory.getLogger(CommonDruidConfig.class);
	    //公用配置
	    @Value("${spring.datasource.druid.driverClassName:#{null}}")
	    private String driverClassName;
	    @Value("${spring.datasource.druid.initialSize:#{null}}")
	    private Integer initialSize;
	    @Value("${spring.datasource.druid.minIdle:#{null}}")
	    private Integer minIdle;
	    @Value("${spring.datasource.druid.maxActive:#{null}}")
	    private Integer maxActive;
	    @Value("${spring.datasource.druid.maxWait:#{null}}")
	    private Integer maxWait;
	    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis:#{null}}")
	    private Integer timeBetweenEvictionRunsMillis;
	    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis:#{null}}")
	    private Integer minEvictableIdleTimeMillis;
	    @Value("${spring.datasource.druid.validationQuery:#{null}}")
	    private String validationQuery;
	    @Value("${spring.datasource.druid.dbType:#{null}}")
	    private String dbType;
	    @Value("${spring.datasource.druid.testWhileIdle:#{null}}")
	    private Boolean testWhileIdle;
	    @Value("${spring.datasource.druid.testOnBorrow:#{null}}")
	    private Boolean testOnBorrow;
	    @Value("${spring.datasource.druid.testOnReturn:#{null}}")
	    private Boolean testOnReturn;
	    @Value("${spring.datasource.druid.poolPreparedStatements:#{null}}")
	    private Boolean poolPreparedStatements;
	    @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize:#{null}}")
	    private Integer maxPoolPreparedStatementPerConnectionSize;
	    @Value("${spring.datasource.druid.filters:#{null}}")
	    private String filters;
	    @Value("{spring.datasource.druid.connectionProperties:#{null}}")
	    private String connectionProperties;
	    
	    public DataSource dataSource(DruidDataSource datasource){
//	        datasource.setUrl(this.dbUrl);
//	        datasource.setUsername(username);
//	        datasource.setPassword(password);
	        datasource.setDriverClassName(driverClassName);
	        //configuration
	        if(initialSize != null) {
	            datasource.setInitialSize(initialSize);
	        }
	        if(minIdle != null) {
	            datasource.setMinIdle(minIdle);
	        }
	        if(maxActive != null) {
	            datasource.setMaxActive(maxActive);
	        }
	        if(maxWait != null) {
	            datasource.setMaxWait(maxWait);
	        }
	        if(timeBetweenEvictionRunsMillis != null) {
	            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	        }
	        if(minEvictableIdleTimeMillis != null) {
	            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	        }
	        if(validationQuery!=null) {
	            datasource.setValidationQuery(validationQuery);
	        }
	        if(dbType!=null) {
	            datasource.setDbType(dbType);
	        }
	        if(testWhileIdle != null) {
	            datasource.setTestWhileIdle(testWhileIdle);
	        }
	        if(testOnBorrow != null) {
	            datasource.setTestOnBorrow(testOnBorrow);
	        }
	        if(testOnReturn != null) {
	            datasource.setTestOnReturn(testOnReturn);
	        }
	        if(poolPreparedStatements != null) {
	            datasource.setPoolPreparedStatements(poolPreparedStatements);
	        }
	        if(maxPoolPreparedStatementPerConnectionSize != null) {
	            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
	        }
	        if(connectionProperties != null) {
	            datasource.setConnectionProperties(connectionProperties);
	        }
	        List<Filter> filters = new ArrayList<>();
	        filters.add(statFilter());
	        filters.add(wallFilter());
	        datasource.setProxyFilters(filters);

	        return datasource;
	    }
	    
	    @Bean
	    public ServletRegistrationBean druidServlet() {
	        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
	        servletRegistrationBean.setServlet(new StatViewServlet());
	        servletRegistrationBean.addUrlMappings("/druid/*");
	        return servletRegistrationBean;
	    }

	    @Bean
	    public StatFilter statFilter(){
	        StatFilter statFilter = new StatFilter();
	        statFilter.setLogSlowSql(true);
	        statFilter.setMergeSql(true);
	        statFilter.setSlowSqlMillis(1000);
	        return statFilter;
	    }

	    @Bean
	    public WallFilter wallFilter(){
	        WallFilter wallFilter = new WallFilter();
	        //允许执行多条SQL
	        WallConfig config = new WallConfig();
	        config.setMultiStatementAllow(true);
	        wallFilter.setConfig(config);
	        return wallFilter;
	    }
}

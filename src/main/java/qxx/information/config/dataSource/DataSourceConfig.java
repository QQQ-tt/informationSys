package qxx.information.config.dataSource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import qxx.information.config.mybatis.MyMetaObjectHandler;

import javax.sql.DataSource;

/**
 * @author qtx
 * @since 2023/11/9 18:32
 */
@Configuration
@MapperScan(value = "qxx.information.mapper")
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Autowired
    private MyMetaObjectHandler myMetaObjectHandler;

    @Bean
    @Primary
    public SqlSessionFactory sysSqlSessionFactory(
            DataSource datasource) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(myMetaObjectHandler);
        globalConfig.setBanner(false);
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        dbConfig.setLogicDeleteField("deleteFlag");
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        globalConfig.setDbConfig(dbConfig);
        bean.setDataSource(datasource);
        bean.setConfiguration(configuration);
        bean.setGlobalConfig(globalConfig);
        bean.setPlugins(mybatisPlusInterceptor);
        bean.setMapperLocations(// 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sysSqlSessionTemplate(
            SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}

package egovframework.cbm.config.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@EnableJpaRepositories(
        basePackages = { DatabaseConstants.BaseDatabase.package_domain, DatabaseConstants.BaseDatabase.package_repository },
        entityManagerFactoryRef = DatabaseConstants.BaseDatabase.entity_manager_factory,
        transactionManagerRef = DatabaseConstants.BaseDatabase.tx_manager
)
@RequiredArgsConstructor
@Configuration("baseDatabaseConfig")
public class BaseDatabaseConfig {

    private final DatabaseProperties dbProps;

    /**
     * DataSource를 생성하여 반환합니다.
     *
     * @return DataSource 객체
     */
    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {

        DatabaseProperties.Base baseInfo = dbProps.getBase();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(baseInfo.getDriver());
        dataSource.setUrl(baseInfo.getUrl());
        dataSource.setUsername(baseInfo.getUsername());
        dataSource.setPassword(baseInfo.getPassword());

        log.info("#################################################");
        log.info("## Database Connection - Base Config Database ");
        log.info("Driver : {}", baseInfo.getDriver());
        log.info("URL : {}", baseInfo.getUrl());
        log.info("USERNAME : {}", baseInfo.getUsername());
        log.info("PASSWORD : {}", baseInfo.getPassword());
        log.info("MaxPoolSize : {}", baseInfo.getMaxPoolSize());
        log.info("#################################################");

        HikariDataSource hds = new HikariDataSource();
        hds.setDataSource(dataSource);
        hds.setPoolName("Base-DB-POOL");
        hds.setMaximumPoolSize(Integer.parseInt(baseInfo.getMaxPoolSize()));

        return hds;

    }

    /**
     * JpaTransactionManager를 생성하여 반환합니다.
     *
     * @param entityManagerFactoryBean LocalContainerEntityManagerFactoryBean 객체
     * @return JpaTransactionManager 객체
     */
    @Primary
    @Bean(name = DatabaseConstants.BaseDatabase.tx_manager)
    @Autowired
    public JpaTransactionManager jpaTransactionManager(
            @Qualifier(value = DatabaseConstants.BaseDatabase.entity_manager_factory) LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());

        return transactionManager;

    }

    /**
     * EntityManagerFactory를 생성하여 반환합니다.
     *
     * @return LocalContainerEntityManagerFactoryBean 객체
     */
    @Primary
    @Bean(name = DatabaseConstants.BaseDatabase.entity_manager_factory)
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        DatabaseProperties.Base baseInfo = dbProps.getBase();

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter jpsAdapter = new HibernateJpaVendorAdapter();
        jpsAdapter.setShowSql(baseInfo.getShowSql().equals("true"));
        entityManagerFactoryBean.setJpaVendorAdapter(jpsAdapter);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(DatabaseConstants.BaseDatabase.package_domain);

        Properties props = new Properties();

        if (!baseInfo.getSchema().isEmpty()) {
            props.setProperty(Environment.DEFAULT_SCHEMA, baseInfo.getSchema());
        }
        props.setProperty(Environment.DIALECT, baseInfo.getDialect());
        props.setProperty(Environment.SHOW_SQL, baseInfo.getShowSql());
        props.setProperty(Environment.FORMAT_SQL, baseInfo.getFormatSql());
        props.setProperty(Environment.HBM2DDL_AUTO, baseInfo.getDdlAuto());
        entityManagerFactoryBean.setJpaProperties(props);

        return entityManagerFactoryBean;

    }

}
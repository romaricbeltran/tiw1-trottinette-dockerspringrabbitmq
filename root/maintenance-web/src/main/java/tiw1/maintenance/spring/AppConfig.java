package tiw1.maintenance.spring;

import org.h2.Driver;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;


@Configuration
@EnableWebMvc
@ComponentScan("tiw1.maintenance")
@EnableTransactionManagement
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger((AppConfig.class));

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(Driver.class);
        ds.setUrl("jdbc:h2:~/maintenance-web");
        return ds;
    }

    @Bean
    public LoadTimeWeaver weaver() {
        return new InstrumentationLoadTimeWeaver();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        LOG.debug("Created EMF");
        emfb.setDataSource(dataSource());
        LOG.debug("set datasource");
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        LOG.debug("set persistence provider");
        emfb.setLoadTimeWeaver(weaver());
        LOG.debug("set weaver");
        emfb.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create-drop");
        emfb.setPackagesToScan("tiw1.maintenance.models");
        return emfb;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource());
        return jpaTransactionManager;
    }
}
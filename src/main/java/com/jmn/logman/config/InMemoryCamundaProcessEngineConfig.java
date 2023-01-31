package com.jmn.logman.config;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class InMemoryCamundaProcessEngineConfig {

    private final ResourcePatternResolver resourceLoader;

    @Autowired
    public InMemoryCamundaProcessEngineConfig(ResourcePatternResolver resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public ProcessEngineFactoryBean appInMemoryProcessEngine() throws IOException {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(appInMemoryProcessEngineConfiguration());
        return factoryBean;
    }

    @Bean
    public SpringProcessEngineConfiguration appInMemoryProcessEngineConfiguration() throws IOException {
        final SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setProcessEngineName("app-bpm-engine-in-memory");
        config.setDataSource(appInMemoryProcessEngineDataSource());
        config.setTransactionManager(appInMemoryProcessEngineTransactionManager());
        config.setDatabaseSchemaUpdate("true");
        config.setJobExecutorActivate(false);

        final Resource[] resources = resourceLoader.getResources("classpath:/processes/in-memory/**/*.bpmn");
        config.setDeploymentResources(resources);

        config.setHistory(HistoryLevel.HISTORY_LEVEL_ACTIVITY.getName());
        config.setMetricsEnabled(false);

        return config;
    }

    @Bean
    public DataSource appInMemoryProcessEngineDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:mem:process-engine;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager appInMemoryProcessEngineTransactionManager() {
        return new DataSourceTransactionManager(appInMemoryProcessEngineDataSource());
    }

    @Bean
    public RepositoryService appInMemoryProcessEngineRepositoryService(ProcessEngine appInMemoryProcessEngine) {
        return appInMemoryProcessEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService appInMemoryProcessEngineRuntimeService(ProcessEngine appInMemoryProcessEngine) {
        return appInMemoryProcessEngine.getRuntimeService();
    }

    @Bean
    public TaskService appInMemoryProcessEngineTaskService(ProcessEngine appInMemoryProcessEngine) {
        return appInMemoryProcessEngine.getTaskService();
    }

    @Bean
    public HistoryService appInMemoryProcessEngineHistoryService(ProcessEngine appInMemoryProcessEngine) {
        return appInMemoryProcessEngine.getHistoryService();
    }
}

package com.quinn.framework.configuration;

import com.quinn.framework.api.file.FileHandler;
import com.quinn.framework.api.file.StorageService;
import com.quinn.framework.component.LocalStorageServiceImpl;
import com.quinn.framework.component.file.DemoFileHandler;
import com.quinn.framework.service.AuditAbleService;
import com.quinn.framework.service.CacheAbleService;
import com.quinn.framework.service.IdGenerateAbleService;
import com.quinn.framework.service.ParameterAbleService;
import com.quinn.framework.service.impl.DefaultAuditAbleServiceImpl;
import com.quinn.framework.service.impl.DefaultCacheAbleServiceImpl;
import com.quinn.framework.service.impl.UuidGenerateAbleServiceImpl;
import com.quinn.framework.service.impl.DefaultParameterAbleServiceImpl;
import com.quinn.util.base.factory.PrefixThreadFactory;
import com.quinn.util.licence.model.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基础配置类：其中的Bean在生产环境中需要根据需要换掉
 *
 * @author Qunhua.Liao
 * @since 2020-03-30
 */
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE - 1000)
public class BaseBizConfiguration {

    @Value("${com.quinn-service.strategy.thread.core-size:2}")
    private int coreTheadSize;

    @Value("${com.quinn-service.strategy.thread.max-size:10}")
    private int maxThreadSize;

    @Value("${com.quinn-service.strategy.thread.queen-size:10}")
    private int queenSize;

    @Bean
    @ConditionalOnMissingBean(name = {"applicationInfo"})
    public ApplicationInfo applicationInfo() {
        return ApplicationInfo.getInstance();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"cacheAbleService"})
    public CacheAbleService cacheAbleService() {
        return new DefaultCacheAbleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"parameterAbleService"})
    public ParameterAbleService parameterAbleService() {
        return new DefaultParameterAbleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"auditAbleService"})
    public AuditAbleService auditAbleService() {
        return new DefaultAuditAbleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"idGenerateAbleService"})
    public IdGenerateAbleService idGenerateAbleService() {
        return new UuidGenerateAbleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"storageService"})
    public StorageService storageService() {
        return new LocalStorageServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value = {FileHandler.class})
    public FileHandler fileHandler(StorageService storageService) {
        return new DemoFileHandler(storageService);
    }

    @Bean
    @ConditionalOnMissingBean(value = {RestTemplate.class})
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"strategyExecutorService"})
    public ExecutorService strategyExecutorService() {
        return new ThreadPoolExecutor(coreTheadSize, maxThreadSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queenSize), new PrefixThreadFactory("strategy-pool-"));
    }

}

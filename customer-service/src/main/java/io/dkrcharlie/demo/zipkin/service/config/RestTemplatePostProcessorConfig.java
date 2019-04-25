package io.dkrcharlie.demo.zipkin.service.config;

import io.dkrcharlie.demo.zipkin.service.filter.RestTemplateInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This processor will be triggered right after the been
 * <code>org.springframework.web.client.RestTemplate</code>
 * had been initialized and it will add
 * <code>io.dkrcharlie.demo.zipkin.service.filter.RestTemplateInterceptor</code>
 * to <code>org.springframework.web.client.RestTemplate</code> instance's interceptor list.
 */
@Configuration
@Slf4j
public class RestTemplatePostProcessorConfig implements BeanPostProcessor {

    @Bean
    RestTemplateInterceptor artRestTemplateInterceptor() {
        return new RestTemplateInterceptor();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof RestTemplate) {
            final RestTemplate restTemplate = (RestTemplate) bean;
            log.info(">>>>>>>> postProcessAfterInitialization of " + beanName);
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            interceptors.add(artRestTemplateInterceptor());
            restTemplate.setInterceptors(interceptors);
            return restTemplate;
        }
        return bean;
    }
}

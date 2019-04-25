package io.dkrcharlie.demo.zipkin.service.filter;

import io.dkrcharlie.demo.zipkin.service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * This advice is responsible for add network timestamp for "the moment response return".
 * We cannot do that in filter due to at that moment the response had been committed.
 */
@ControllerAdvice
@Slf4j
public class HeaderModifierAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info(">>>>>> ARTHeaderModifierAdvice.beforeBodyWrite,add leg3 into response header");
        response.getHeaders().add(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3,String.valueOf(System.currentTimeMillis()));
        return body;
    }

}

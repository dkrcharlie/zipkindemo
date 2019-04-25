package io.dkrcharlie.demo.zipkin.service.filter;

import brave.Span;
import brave.Tracer;
import io.dkrcharlie.demo.zipkin.service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    Tracer tracer;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        log.info(">>>>>> traceRequest add art leg1 and request size into span tag");
        HttpHeaders httpHeaders = request.getHeaders();
        Long request_size = httpHeaders.getContentLength();
        log.info(">>>>>> request_size = " + request_size);
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan != null) {
            log.info(">>>>>> current span is not null,set request size and leg1");
            currentSpan.tag(Constants.ART_HEADER_REQUEST_SIZE, String.valueOf(request_size));
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_1, String.valueOf(System.currentTimeMillis()));
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        log.info(">>>>>> traceRequest add art leg4 and response size into span tag");
        HttpHeaders httpHeaders = response.getHeaders();
        Long response_size = httpHeaders.getContentLength();
        log.info(">>>>>> response_size = " + response_size);
        String art_leg2 = "-1";
        String art_leg3 = "-1";
        if(httpHeaders.get(Constants.ART_HEADER_NET_TIMESTAMP_LEG_2) != null){
            art_leg2 = httpHeaders.get(Constants.ART_HEADER_NET_TIMESTAMP_LEG_2).get(0);
        }
        if(httpHeaders.get(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3) != null){
            art_leg3 = httpHeaders.get(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3).get(0);
        }
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            log.info(">>>>>> current span is not null,set response size leg2,leg3 and leg4");
            currentSpan.tag(Constants.ART_HEADER_RESPONSE_SIZE,String.valueOf(response_size));
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_2, art_leg2);
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3, art_leg3);
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_4, String.valueOf(System.currentTimeMillis()));
        }
    }
}

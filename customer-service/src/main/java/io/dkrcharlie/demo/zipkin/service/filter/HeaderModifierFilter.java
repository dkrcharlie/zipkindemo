package io.dkrcharlie.demo.zipkin.service.filter;

import brave.Span;
import brave.Tracer;
import io.dkrcharlie.demo.zipkin.service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class responsible for adding timestamp for "the moment that request coming"
 * and get timestamp for "the moment response return" from header of response and set into
 * current zipkin span.
 */
@Component
@Order(1)
class HeaderModifierFilter implements Filter {

    @Autowired
    Tracer tracer;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // Cache ART leg2
        String art_leg2 = String.valueOf(System.currentTimeMillis());
        // Add ART leg2
        ((HttpServletResponse) response).addHeader(Constants.ART_HEADER_NET_TIMESTAMP_LEG_2,
                art_leg2);
        chain.doFilter(request, response);

        // Add ART leg2 and leg3 to zipkin span tags
        String art_leg3 = ((HttpServletResponse) response).getHeader(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3);
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan != null) {
            currentSpan.tag(Constants.ART_HEADER_REQUEST_SIZE, String.valueOf(((HttpServletRequest) request).getContentLength()));
            currentSpan.tag(Constants.ART_HEADER_RESPONSE_SIZE, ((HttpServletResponse) response).getHeader("Content-Length"));
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_2, art_leg2);
            currentSpan.tag(Constants.ART_HEADER_NET_TIMESTAMP_LEG_3, art_leg3);
        }
    }

}

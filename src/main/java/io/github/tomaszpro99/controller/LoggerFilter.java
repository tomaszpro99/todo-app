package io.github.tomaszpro99.controller; //chcemy dodac loggowanie

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE) //v2
class LoggerFilter implements Filter/*, Ordered*/ { //Ordered do uporządkowania w kolejnosci
    public static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class); //tworzymy logger
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        //logika do filtrowania
        if(request instanceof HttpServletRequest) { //gdy request'em jest HttpServletRequest
            var httpRequest = (HttpServletRequest) request;
            logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        }
        chain.doFilter(request, response);
        //logger.info("[doFilter] done.");
    }

//    @Override
//    public int getOrder() { //Ordered: metoda do zwracania priorytetow
//        //return 0;
//        return Ordered.HIGHEST_PRECEDENCE; //najwyzszy priorytet / najnizsza liczba
//    }
}
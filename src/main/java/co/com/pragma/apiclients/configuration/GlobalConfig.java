package co.com.pragma.apiclients.configuration;

import feign.codec.Encoder;
import feign.codec.Decoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.server.WebFilter;


import java.util.Objects;

@EnableAsync(proxyTargetClass = true)
@Configuration
public class GlobalConfig {
   @Value(value = "${spring.webflux.base-path}")
   private String basePath;

   @Bean
   WebFilter webFilter() {
      return (exchange, chain) -> {
         var request = exchange.getRequest();
         var response = exchange.getResponse();
         var headers = response.getHeaders();
         if (Objects.equals(request.getMethod(), HttpMethod.OPTIONS)) {
            headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
         }
         return chain.filter(exchange.mutate().request(request.mutate().contextPath(basePath).build()).build());
      };
   }
   private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

   @Bean
   Encoder feignFormEncoder() {
      return new SpringFormEncoder(new SpringEncoder(messageConverters));
   }

   @Bean
   Decoder feignFormDecoder() {
      return new SpringDecoder(messageConverters);
   }
}
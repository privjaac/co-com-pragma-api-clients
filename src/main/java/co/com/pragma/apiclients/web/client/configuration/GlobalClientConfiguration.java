package co.com.pragma.apiclients.web.client.configuration;

import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import feign.Client;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
public class GlobalClientConfiguration implements Serializable {
   private static final long serialVersionUID = -2087728395759583642L;
   private final GlobalHelper helper;

   @Lazy
   public GlobalClientConfiguration(GlobalHelper helper) {
      this.helper = helper;
   }

   public class UnsecuredTSL implements Serializable {
      private static final long serialVersionUID = -5424138405094442533L;

      @Bean
      public Client client() throws NoSuchAlgorithmException, KeyManagementException {
         return new Client.Default(helper.initUnsecuredTSLContext().getSocketFactory(), new NoopHostnameVerifier());
      }
   }

   public class MultiPartSupport {

      // @Bean // no usarlo.... temp..
      @Scope("prototype")
      public Encoder feignFormEncoder() {
         return new SpringFormEncoder();
      }
   }
}

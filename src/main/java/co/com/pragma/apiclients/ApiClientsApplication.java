package co.com.pragma.apiclients;

import co.com.pragma.apiclients.web.client.configuration.GlobalClientConfiguration.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(defaultConfiguration = UnsecuredTSL.class)
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ApiClientsApplication {
   public static void main(String[] args) {
      SpringApplication.run(ApiClientsApplication.class, args);
   }
}
package co.com.pragma.apiclients.configuration.scheduled;

import co.com.pragma.apiclients.configuration.constant.PathConstant;
import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import co.com.pragma.apiclients.model.dto.photo.PhotoDto;
import co.com.pragma.apiclients.web.client.feign.photos.PhotoClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Component
public class TestScheduled implements Serializable {
   private static final long serialVersionUID = 5662387365681816422L;
   private final GlobalHelper helper;
   private final PhotoClient photoClient;

   @Lazy
   public TestScheduled(GlobalHelper helper, PhotoClient photoClient) {
      this.helper = helper;
      this.photoClient = photoClient;
   }

//   @Scheduled(initialDelay = 0, fixedDelay = 999999999)
   void testPropertiesBoostrap() {
      try {
         var model = PhotoDto.builder()
                 .clientId(UUID.randomUUID())
                 .photoBase64(Base64.encodeBase64String(helper.getBytesFromFile(new File(PathConstant.PATH_TEMP.concat("bg-zoom-01.jpg")))))
                 .status(true)
                 .build();
        var response = photoClient.addEdit(model);
        if (!response.getFlag()) {
           System.out.println(response.getMessage());
           return;
        };
         System.out.println("finish process, " + response.getMessage());
      } catch (FeignException fe) {
         System.out.println(fe.getMessage());
      }
   }
}

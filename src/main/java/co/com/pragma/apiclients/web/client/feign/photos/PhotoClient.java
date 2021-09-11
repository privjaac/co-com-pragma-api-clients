package co.com.pragma.apiclients.web.client.feign.photos;

import co.com.pragma.apiclients.configuration.helper.general.model.Response;
import co.com.pragma.apiclients.model.dto.photo.PhotoDto;
import co.com.pragma.apiclients.model.vo.photo.PhotoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${app.api.client.photo.name}", url = "${app.api.client.photo.url}/photo")
public interface PhotoClient {
   @PostMapping(value = "/add-edit")
   Response<PhotoVo> addEdit(@RequestBody PhotoDto model);

   @GetMapping(value = "/one/client/{id}")
   Response<PhotoVo> oneByClientId(@PathVariable String id);
}
package co.com.pragma.apiclients.web.api;

import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import co.com.pragma.apiclients.configuration.helper.general.model.Response;
import co.com.pragma.apiclients.configuration.helper.general.svc.ControllerSvc;
import co.com.pragma.apiclients.model.dto.client.ClientDto;
import co.com.pragma.apiclients.model.dto.client.ClientSearchDto;
import co.com.pragma.apiclients.model.vo.client.ClientVo;
import co.com.pragma.apiclients.service.svc.ClientSvc;
import co.com.pragma.apiclients.web.exception.GlobalException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/client")
public class ClientCtrl implements ControllerSvc<ClientVo, ClientDto, ClientSearchDto, UUID> {
   private final GlobalHelper helper;
   private final ClientSvc svc;

   @Lazy
   public ClientCtrl(GlobalHelper helper, ClientSvc svc) {
      this.helper = helper;
      this.svc = svc;
   }

   @Override
   public Response<ClientVo> get(UUID id) throws GlobalException {
      return svc.get(id);
   }

   @Override
   public Response<ClientVo> add(@ModelAttribute ClientDto model) throws GlobalException {
      return svc.add(model);
   }

   @Override
   public Response<ClientVo> edit(@ModelAttribute ClientDto model, UUID id) throws GlobalException {
      return svc.edit(model, id);
   }

   @Override
   public Response<ClientVo> del(UUID id) throws GlobalException {
      return svc.del(id);
   }

   @Override
   public Page<ClientVo> page(ClientSearchDto model, Integer pageNumber, Integer pageSize) {
      return svc.page(model, helper.getDefaultPageable(pageNumber, pageSize));
   }

   @Override
   public List<ClientVo> all(ClientSearchDto model, Integer limit) {
      return svc.all(model, limit);
   }
}
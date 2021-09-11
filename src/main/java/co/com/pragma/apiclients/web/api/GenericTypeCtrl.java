package co.com.pragma.apiclients.web.api;

import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import co.com.pragma.apiclients.configuration.helper.general.model.Response;
import co.com.pragma.apiclients.configuration.helper.general.svc.ControllerSvc;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeDto;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeSearchDto;
import co.com.pragma.apiclients.model.vo.generictype.GenericTypeVo;
import co.com.pragma.apiclients.service.svc.GenericTypeSvc;
import co.com.pragma.apiclients.web.exception.GlobalException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/generic-type")
public class GenericTypeCtrl implements ControllerSvc<GenericTypeVo, GenericTypeDto, GenericTypeSearchDto, UUID> {
   private final GlobalHelper helper;
   private final GenericTypeSvc svc;

   @Lazy
   public GenericTypeCtrl(GlobalHelper helper, GenericTypeSvc svc) {
      this.helper = helper;
      this.svc = svc;
   }

   @Override
   public Response<GenericTypeVo> get(UUID id) throws GlobalException {
      return svc.get(id);
   }

   @Override
   public Response<GenericTypeVo> add(GenericTypeDto model) throws GlobalException {
      return svc.add(model);
   }

   @Override
   public Response<GenericTypeVo> edit(GenericTypeDto model, UUID id) throws GlobalException {
      return svc.edit(model, id);
   }

   @Override
   public Response<GenericTypeVo> del(UUID id) throws GlobalException {
      return svc.del(id);
   }

   @Override
   public Page<GenericTypeVo> page(GenericTypeSearchDto model, Integer pageNumber, Integer pageSize) {
      return svc.page(model, helper.getDefaultPageable(pageNumber, pageSize));
   }

   @Override
   public List<GenericTypeVo> all(GenericTypeSearchDto model, Integer limit) {
      return svc.all(model, limit);
   }
}
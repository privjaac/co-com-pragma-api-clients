package co.com.pragma.apiclients.configuration.helper;

import co.com.pragma.apiclients.configuration.helper.general.svc.HelperSvc;
import co.com.pragma.apiclients.model.persistence.entity.GenericType;
import co.com.pragma.apiclients.model.persistence.repo.GenericTypeRepo;
import co.com.pragma.apiclients.model.vo.generictype.GenericTypeVo;
import co.com.pragma.apiclients.web.exception.GlobalException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenericTypeHelper implements HelperSvc<GenericTypeVo, GenericType, UUID, GlobalException> {
   private final GlobalHelper helper;
   private final GenericTypeRepo repo;

   @Lazy
   public GenericTypeHelper(GlobalHelper helper, GenericTypeRepo repo) {
      this.helper = helper;
      this.repo = repo;
   }

   @Override
   public GenericTypeVo get(GenericType model) {
      if (!helper.isNotNull(model)) return null;
      return GenericTypeVo.builder()
              .id(model.getId())
              .name(model.getName())
              .status(model.getStatus())
              .created(helper.emitNullDateTimeIN(model.getCreated()))
              .updated(helper.emitNullDateTimeIN(model.getUpdated()))
              .build();
   }

   @Override
   public GenericType get(UUID uuid, String errorMessage) throws GlobalException {
      return helper.getEntity(uuid, errorMessage, repo);
   }
}
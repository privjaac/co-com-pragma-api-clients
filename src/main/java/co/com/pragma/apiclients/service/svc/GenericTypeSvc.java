package co.com.pragma.apiclients.service.svc;

import co.com.pragma.apiclients.configuration.helper.general.svc.GlobalSvc;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeDto;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeSearchDto;
import co.com.pragma.apiclients.model.vo.generictype.GenericTypeVo;

import java.util.UUID;

public interface GenericTypeSvc extends GlobalSvc<GenericTypeVo, GenericTypeDto, GenericTypeSearchDto, UUID> {
}
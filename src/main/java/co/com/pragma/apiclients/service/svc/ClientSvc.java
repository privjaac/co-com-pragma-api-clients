package co.com.pragma.apiclients.service.svc;

import co.com.pragma.apiclients.configuration.helper.general.svc.GlobalSvc;
import co.com.pragma.apiclients.model.dto.client.ClientDto;
import co.com.pragma.apiclients.model.dto.client.ClientSearchDto;
import co.com.pragma.apiclients.model.vo.client.ClientVo;

import java.util.UUID;

public interface ClientSvc extends GlobalSvc<ClientVo, ClientDto, ClientSearchDto, UUID> {
}
package co.com.pragma.apiclients.service.impl;

import co.com.pragma.apiclients.configuration.constant.MessageConstant;
import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import co.com.pragma.apiclients.configuration.helper.general.model.Response;
import co.com.pragma.apiclients.model.dto.client.ClientDto;
import co.com.pragma.apiclients.model.dto.client.ClientSearchDto;
import co.com.pragma.apiclients.model.persistence.entity.Client;
import co.com.pragma.apiclients.model.persistence.entity.QClient;
import co.com.pragma.apiclients.model.persistence.repo.ClientRepo;
import co.com.pragma.apiclients.model.vo.client.ClientVo;
import co.com.pragma.apiclients.service.svc.ClientSvc;
import co.com.pragma.apiclients.web.exception.GlobalException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientImpl implements ClientSvc {
   private final GlobalHelper helper;
   private final ClientRepo repo;
   private final String entityName = "client's";

   @Lazy
   public ClientImpl(GlobalHelper helper, ClientRepo repo) {
      this.helper = helper;
      this.repo = repo;
   }

   @Override
   public Response<ClientVo> get(UUID uuid) throws GlobalException {
      var response = helper.basicResponse(ClientVo.class, MessageConstant.existSuccess.apply(entityName));
      var client = helper.getClient().get(uuid, MessageConstant.existError.apply(entityName));
      response.setEntity(helper.getClient().get(client));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<ClientVo> add(ClientDto model) throws GlobalException {
      var response = helper.basicResponse(ClientVo.class, MessageConstant.addSuccess.apply(entityName));
      var client = this.fillFields(model, new Client());
      repo.save(client);
      helper.getClient().addEditPhoto(client, model.getPhoto());
      response.setEntity(helper.getClient().get(client));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<ClientVo> edit(ClientDto model, UUID uuid) throws GlobalException {
      var response = helper.basicResponse(ClientVo.class, MessageConstant.editSuccess.apply(entityName));
      var client = this.fillFields(model, helper.getClient().get(uuid, MessageConstant.editError.apply(entityName)));
      repo.save(client);
      helper.getClient().addEditPhoto(client, model.getPhoto());
      response.setEntity(helper.getClient().get(client));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<ClientVo> del(UUID uuid) throws GlobalException {
      var response = helper.basicResponse(ClientVo.class, MessageConstant.deleteSuccess.apply(entityName));
      var client = helper.getClient().get(uuid, MessageConstant.existError.apply(entityName));
      repo.delete(client);
      response.setEntity(helper.getClient().get(client));
      return response;
   }

   @Override
   public Page<ClientVo> page(ClientSearchDto model, Pageable pageable) {
      var condition = this.listCondition(model);
      var page = repo.findAll(condition, pageable);
      page = this.helper.getPageImpl(page, pageable);
      return page.map(helper.getClient()::get);
   }

   @Override
   public List<ClientVo> all(ClientSearchDto model, Integer limit) {
      var tb = QClient.client;
      var condition = this.listCondition(model);
      var listQuery = helper.query().select(tb).from(tb).where(condition);
      if (helper.isNotNull(limit)) listQuery.limit(limit);
      var list = listQuery.fetch().stream();
      return list.map(helper.getClient()::get).collect(Collectors.toList());
   }

   private Client fillFields(ClientDto model, Client client) throws GlobalException {
      if (helper.isNotNull(model.getName())) client.setName(model.getName());
      if (helper.isNotNull(model.getLastName())) client.setLastName(model.getLastName());
      if (helper.isNotNull(model.getTypeIdentificationId())) client.setTypeIdentification(helper.getGenericType().get(model.getTypeIdentificationId(), MessageConstant.existError.apply(GenericTypeImpl.entityName)));
      if(helper.isNotNull(model.getNumberIdentification())) client.setNumberIdentification(model.getNumberIdentification());
      if (helper.isNotNull(model.getAge())) client.setAge(model.getAge());
      if (helper.isNotNull(model.getCityBirth())) client.setCityBirth(model.getCityBirth());
      if (helper.isNotNull(model.getStatus())) client.setStatus(model.getStatus());
      return client;
   }

   private Predicate listCondition(ClientSearchDto model) {
      var tb = QClient.client;
      var condition = new BooleanBuilder();
      if (helper.isNotNull(model.getName())) condition.and(tb.name.lower().contains(model.getName()));
      if (helper.isNotNull(model.getLastName())) condition.and(tb.name.lower().contains(model.getLastName()));
      if (helper.isNotNull(model.getTypeIdentification())) condition.and(tb.name.lower().contains(model.getTypeIdentification()));
      if (helper.isNotNull(model.getNumberIdentification())) condition.and(tb.numberIdentification.lower().contains(model.getNumberIdentification()));
      if (helper.isNotNull(model.getAge())) condition.and(this.ageCondition(condition, model.getAge()));
      if (helper.isNotNull(model.getCityBirth())) condition.and(tb.name.lower().contains(model.getCityBirth()));
      if (helper.isNotNull(model.getStatus())) condition.and(tb.status.eq(model.getStatus()));
      if (helper.isNotNull(model.getCreated())) condition.and(tb.created.stringValue().contains(model.getCreated()));
      if (helper.isNotNull(model.getUpdated())) condition.and(tb.updated.stringValue().contains(model.getUpdated()));
      return condition;
   }

   private Predicate ageCondition(BooleanBuilder condition, String simbolAge) {
      var tb = QClient.client;
      var splitAge = simbolAge.trim().split("\\s");
      var lengthSplitAge = splitAge.length;
      if (lengthSplitAge == 0) {
         condition.and(tb.age.eq(Integer.parseInt(simbolAge.trim())));
         return condition;
      }
      var symbol = splitAge[0];
      var value = splitAge[1];
      BooleanExpression expressionAge = null;
      switch (symbol) {
         case ">=":
            expressionAge = tb.age.goe(Integer.parseInt(value));
            break;
         case "<=":
            expressionAge = tb.age.loe(Integer.parseInt(value));
            break;
         case ">":
            expressionAge = tb.age.gt(Integer.parseInt(value));
            break;
         case "<":
            expressionAge = tb.age.lt(Integer.parseInt(value));
            break;
         default:
            return condition;
      }
      condition.and(expressionAge);
      return condition;
   }
}
package co.com.pragma.apiclients.service.impl;

import co.com.pragma.apiclients.configuration.constant.MessageConstant;
import co.com.pragma.apiclients.configuration.helper.GlobalHelper;
import co.com.pragma.apiclients.configuration.helper.general.model.Response;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeDto;
import co.com.pragma.apiclients.model.dto.generictype.GenericTypeSearchDto;
import co.com.pragma.apiclients.model.persistence.entity.GenericType;
import co.com.pragma.apiclients.model.persistence.entity.QGenericType;
import co.com.pragma.apiclients.model.persistence.repo.GenericTypeRepo;
import co.com.pragma.apiclients.model.vo.generictype.GenericTypeVo;
import co.com.pragma.apiclients.service.svc.GenericTypeSvc;
import co.com.pragma.apiclients.web.exception.GlobalException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GenericTypeImpl implements GenericTypeSvc {
   private final GlobalHelper helper;
   private final GenericTypeRepo repo;
   protected final static String entityName = "generic type's";

   @Lazy
   public GenericTypeImpl(GlobalHelper helper, GenericTypeRepo repo) {
      this.helper = helper;
      this.repo = repo;
   }

   @Override
   public Response<GenericTypeVo> get(UUID uuid) throws GlobalException {
      var response = helper.basicResponse(GenericTypeVo.class, MessageConstant.existSuccess.apply(entityName));
      var genericType = helper.getGenericType().get(uuid, MessageConstant.existError.apply(entityName));
      response.setEntity(helper.getGenericType().get(genericType));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<GenericTypeVo> add(GenericTypeDto model) throws GlobalException {
      var response = helper.basicResponse(GenericTypeVo.class, MessageConstant.addSuccess.apply(entityName));
      var genericType = this.fillFields(model, new GenericType());
      repo.save(genericType);
      response.setEntity(helper.getGenericType().get(genericType));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<GenericTypeVo> edit(GenericTypeDto model, UUID uuid) throws GlobalException {
      var response = helper.basicResponse(GenericTypeVo.class, MessageConstant.editSuccess.apply(entityName));
      var genericType = this.fillFields(model, helper.getGenericType().get(uuid, MessageConstant.editError.apply(entityName)));
      repo.save(genericType);
      response.setEntity(helper.getGenericType().get(genericType));
      return response;
   }

   @Transactional(rollbackFor = GlobalException.class)
   @Override
   public Response<GenericTypeVo> del(UUID uuid) throws GlobalException {
      var response = helper.basicResponse(GenericTypeVo.class, MessageConstant.deleteSuccess.apply(entityName));
      var genericType = helper.getGenericType().get(uuid, MessageConstant.existError.apply(entityName));
      repo.delete(genericType);
      response.setEntity(helper.getGenericType().get(genericType));
      return response;
   }

   @Override
   public Page<GenericTypeVo> page(GenericTypeSearchDto model, Pageable pageable) {
      var condition = this.listCondition(model);
      var page = repo.findAll(condition, pageable);
      page = helper.getPageImpl(page, pageable);
      return page.map(helper.getGenericType()::get);
   }

   @Override
   public List<GenericTypeVo> all(GenericTypeSearchDto model, Integer limit) {
      var tb = QGenericType.genericType;
      var condition = this.listCondition(model);
      var listQuery = helper.query().select(tb).from(tb).where(condition);
      if (helper.isNotNull(limit)) listQuery.limit(limit);
      var list = listQuery.fetch().stream();
      return list.map(helper.getGenericType()::get).collect(Collectors.toList());
   }

   private GenericType fillFields(GenericTypeDto model, GenericType genericType) throws GlobalException {
      if (helper.isNotNull(model.getName())) genericType.setName(model.getName());
      if (helper.isNotNull(model.getEntity())) genericType.setEntity(model.getEntity());
      if (helper.isNotNull(model.getGenericTypeParentId())) genericType.setGenericTypeParentId(model.getGenericTypeParentId());
      if (helper.isNotNull(model.getStatus())) genericType.setStatus(model.getStatus());
      return genericType;
   }

   private Predicate listCondition(GenericTypeSearchDto model) {
      var tb = QGenericType.genericType;
      var condition = new BooleanBuilder();
      if (helper.isNotNull(model.getName())) condition.and(tb.name.lower().contains(model.getName()));
      if (helper.isNotNull(model.getEntity())) condition.and(tb.entity.lower().contains(model.getEntity()));
      if (helper.isNotNull(model.getGenericTypeParent())) condition.and(Expressions.stringOperation(Ops.STRING_CAST, tb.genericTypeParentId).contains(model.getGenericTypeParent()));
      if (helper.isNotNull(model.getStatus())) condition.and(tb.status.eq(model.getStatus()));
      if (helper.isNotNull(model.getCreated())) condition.and(tb.created.stringValue().contains(model.getCreated()));
      if (helper.isNotNull(model.getUpdated())) condition.and(tb.updated.stringValue().contains(model.getUpdated()));
      return condition;
   }
}
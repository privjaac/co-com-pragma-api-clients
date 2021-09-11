package co.com.pragma.apiclients.model.persistence.repo;

import co.com.pragma.apiclients.model.persistence.entity.GenericType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenericTypeRepo extends JpaRepository<GenericType, UUID>, JpaSpecificationExecutor<GenericType>, QuerydslPredicateExecutor<GenericType> {
}
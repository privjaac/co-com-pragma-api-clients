package co.com.pragma.apiclients.model.persistence.repo;

import co.com.pragma.apiclients.model.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepo extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client>, QuerydslPredicateExecutor<Client> {
}
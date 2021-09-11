package co.com.pragma.apiclients.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(schema = "src_information", name = "generic_type")
@EntityListeners(value = {AuditingEntityListener.class})
public class GenericType implements Serializable {
   private static final long serialVersionUID = 1919664312001380762L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   private String name;
   private String entity;
   private UUID genericTypeParentId;
   private Boolean status;
   @CreatedDate
   private LocalDateTime created;
   @LastModifiedDate
   private LocalDateTime updated;
}
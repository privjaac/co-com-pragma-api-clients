package co.com.pragma.apiclients.model.dto.generictype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class GenericTypeDto implements Serializable {
   private static final long serialVersionUID = 5017884002316383826L;
   private String name;
   private String entity;
   private UUID genericTypeParentId;
   private Boolean status;
}
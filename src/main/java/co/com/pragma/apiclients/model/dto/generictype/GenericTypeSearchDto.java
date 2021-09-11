package co.com.pragma.apiclients.model.dto.generictype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class GenericTypeSearchDto implements Serializable {
   private static final long serialVersionUID = 2044157328609774836L;
   private String name;
   private String entity;
   private String genericTypeParent;
   private Boolean status;
   private String created;
   private String updated;
}
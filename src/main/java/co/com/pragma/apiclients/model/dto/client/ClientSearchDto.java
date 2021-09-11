package co.com.pragma.apiclients.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ClientSearchDto implements Serializable {
   private static final long serialVersionUID = -6333897444350831977L;
   private String name;
   private String lastName;
   private String typeIdentification;
   private String numberIdentification;
   private String age; // include symbols -->> <= >= < >
   private String cityBirth;
   private Boolean status;
   private String created;
   private String updated;
}

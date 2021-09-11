package co.com.pragma.apiclients.model.vo.generictype;

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
public class GenericTypeVo implements Serializable {
   private static final long serialVersionUID = 8342019918028792459L;
   private UUID id;
   private String name;
   private Boolean status;
   private String created;
   private String updated;
}
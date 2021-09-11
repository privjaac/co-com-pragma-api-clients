package co.com.pragma.apiclients.model.vo.client;

import co.com.pragma.apiclients.model.vo.generictype.GenericTypeVo;
import co.com.pragma.apiclients.model.vo.photo.PhotoVo;
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
public class ClientVo implements Serializable {
   private static final long serialVersionUID = -656764609581952773L;
   private UUID id;
   private String name;
   private String lastName;
   private GenericTypeVo typeIdentification;
   private String numberIdentification;
   private Integer age;
   private String cityBirth;
   private Boolean status;
   private String created;
   private String updated;
   private PhotoVo photo;
}
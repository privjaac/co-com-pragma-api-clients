package co.com.pragma.apiclients.model.vo.photo;

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
public class PhotoVo implements Serializable {
   private static final long serialVersionUID = -7143677205810174147L;
   private String id;
   private String photo;
   private Boolean status;
   private String created;
   private String updated;
}
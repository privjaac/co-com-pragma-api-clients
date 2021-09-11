package co.com.pragma.apiclients.model.dto.photo;

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
public class PhotoDto implements Serializable {
   private static final long serialVersionUID = 5612311874654681763L;
   private UUID clientId;
   private String photoBase64;
   private Boolean status;
}
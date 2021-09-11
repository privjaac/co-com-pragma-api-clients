package co.com.pragma.apiclients.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ClientDto implements Serializable {
   private static final long serialVersionUID = 5585148243770371665L;
   private String name;
   private String lastName;
   private UUID typeIdentificationId;
   private String numberIdentification;
   private Integer age;
   private String cityBirth;
   private Boolean status;
   private FilePart photo;
}
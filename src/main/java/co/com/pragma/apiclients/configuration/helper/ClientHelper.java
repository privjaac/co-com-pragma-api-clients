package co.com.pragma.apiclients.configuration.helper;

import co.com.pragma.apiclients.configuration.constant.PathConstant;
import co.com.pragma.apiclients.configuration.helper.general.svc.HelperSvc;
import co.com.pragma.apiclients.model.dto.photo.PhotoDto;
import co.com.pragma.apiclients.model.persistence.entity.Client;
import co.com.pragma.apiclients.model.persistence.repo.ClientRepo;
import co.com.pragma.apiclients.model.vo.client.ClientVo;
import co.com.pragma.apiclients.model.vo.photo.PhotoVo;
import co.com.pragma.apiclients.web.client.feign.photos.PhotoClient;
import co.com.pragma.apiclients.web.exception.GlobalException;
import feign.FeignException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class ClientHelper implements HelperSvc<ClientVo, Client, UUID, GlobalException> {
   private final GlobalHelper helper;
   private final ClientRepo repo;
   private final PhotoClient photoClient;

   @Lazy
   public ClientHelper(GlobalHelper helper, ClientRepo repo, PhotoClient photoClient) {
      this.helper = helper;
      this.repo = repo;
      this.photoClient = photoClient;
   }

   @Override
   public ClientVo get(Client model) {
      if (!helper.isNotNull(model)) return null;
      return ClientVo.builder()
              .id(model.getId())
              .name(model.getName())
              .lastName(model.getLastName())
              .typeIdentification(helper.getGenericType().get(model.getTypeIdentification()))
              .numberIdentification(model.getNumberIdentification())
              .age(model.getAge())
              .cityBirth(model.getCityBirth())
              .status(model.getStatus())
              .created(helper.emitNullDateTimeIN(model.getCreated()))
              .updated(helper.emitNullDateTimeIN(model.getUpdated()))
              .photo(this.getPhoto(model.getId().toString()))
              .build();
   }

   @Override
   public Client get(UUID uuid, String errorMessage) throws GlobalException {
      return helper.getEntity(uuid, errorMessage, repo);
   }

   public void addEditPhoto(Client client, FilePart photo) {
      if (!helper.isNotNull(photo)) return;
      try {
         var filePhotoTmp = new File(PathConstant.PATH_TEMP.concat(photo.filename()));
         photo.transferTo(filePhotoTmp).toProcessor().block();
         var model = PhotoDto.builder()
                 .clientId(client.getId())
                 .photoBase64(Base64.getEncoder().encodeToString(helper.getBytesFromFile(filePhotoTmp)))
                 .status(true)
                 .build();
         photoClient.addEdit(model);
      } catch (FeignException ignored) {
      }
   }

   private String getPhotoBase64(String clientId) {
      var photo = this.getPhoto(clientId);
      if (Objects.isNull(photo)) return "none";
      else return photo.getPhoto();
   }

   private PhotoVo getPhoto(String clientId) {
      try {
         var response = photoClient.oneByClientId(clientId);
         if (!response.getFlag()) return null;
         return response.getEntity();
      } catch (FeignException ignored) {
         return null;
      }
   }
}
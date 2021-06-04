package com.iuh.Controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadImage {

    @RequestMapping(value="getimage/{photo}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo){
        if(!photo.equals("") || photo !=null){
            try {
                Path filename= Paths.get("images",photo);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource=new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .contentType(MediaType.parseMediaType("image/jpg"))
                        .body(byteArrayResource);
            }catch (Exception e){

            }
        }
        return ResponseEntity.badRequest().build();

    }

    @RequestMapping(value="nguoiban/getimagenguoiban/{photo}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> getImagenguoiban(@PathVariable("photo") String photo){
        if(!photo.equals("") || photo !=null){
            try {
                Path filename= Paths.get("images",photo);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource=new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .contentType(MediaType.parseMediaType("image/jpg"))
                        .body(byteArrayResource);
            }catch (Exception e){

            }
        }
        return ResponseEntity.badRequest().build();

    }

}

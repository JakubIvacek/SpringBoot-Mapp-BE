package com.stu.fiit.mtaa.be.avatars;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="avatars")
@RequiredArgsConstructor
public class AvatarController {

        private final AvatarService avatarService;

         @GetMapping
         public ResponseEntity<List<byte[]>> getAllAvatarsImages(){
             return ResponseEntity.ok(avatarService.getAllAvatars());
         }

        @GetMapping("/{id}")
        public ResponseEntity<?> downloadAvatar(@PathVariable Long id){
            byte[] imageData=avatarService.downloadImage(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);
        }


}

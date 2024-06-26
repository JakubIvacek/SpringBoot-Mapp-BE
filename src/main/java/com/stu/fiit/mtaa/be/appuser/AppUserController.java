package com.stu.fiit.mtaa.be.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    // Return user
    @GetMapping(path = "/me")
    public ResponseEntity<AppUserResponse> getAppUser(Authentication auth){
        return ResponseEntity.ok().body(appUserService.getAppUser((AppUser) auth.getPrincipal()));
    }

    // Modify user
    @PutMapping
    public ResponseEntity<?> updateAppUser(
            @RequestBody UpdateAppUserRequest updatedUser,
            Authentication auth)
    {
        AppUser user = (AppUser) auth.getPrincipal();
        appUserService.updateAppUser(user, updatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body((AppUser) auth.getPrincipal());
    }

    @PutMapping(path = "/logout")
    public ResponseEntity<Void> logoutUser(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        appUserService.logoutUser(user);
        return ResponseEntity.ok().build();
    }

    // Modify user avatar
    @PutMapping(path = "/avatar/{avatarId}")
    public ResponseEntity<HttpStatus> updateAppUserAvatar(
            @PathVariable Long avatarId,
            Authentication auth)
    {
        try {
            AppUser user = (AppUser) auth.getPrincipal();
            appUserService.updateAppUserAvatar(user, avatarId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
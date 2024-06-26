package com.stu.fiit.mtaa.be.appuser;

import com.stu.fiit.mtaa.be.avatars.AvatarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AvatarRepository avatarRepository;


    public AppUserResponse getAppUser(AppUser user) {
        AppUserResponse response = new AppUserResponse();
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setAge(user.getAge());

//        if (user.getAvatar_id() == null) {
//            response.setImage(null);
//        }else {
//            response.setImage(avatarRepository.findById(user.getAvatar_id()).get().getImage());
//        }

        return response;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found : " + username));
    }

    // Get user FCM Token
    public String getUserFCMToken(String username) {
        String token = appUserRepository.findByUsername(username).get().getFcmToken();
        return token.replace("\"", "");
    }

    @Transactional
    public void updateAppUser(AppUser user, UpdateAppUserRequest updatedUser) {

        user.setFullName(updatedUser.getFullName());
        user.setAge(updatedUser.getAge());

        appUserRepository.save(user);
    }

    @Transactional
    public void updateAppUserAvatar(AppUser user, Long avatarId) {

        if (!avatarRepository.existsById(avatarId)) {
            throw new IllegalArgumentException("Avatar not found : " + avatarId);
        }
        user.setAvatar_id(avatarId);
        appUserRepository.save(user);
    }

    public void logoutUser(AppUser user) {
        user.setFcmToken(null);
        appUserRepository.save(user);
    }
}

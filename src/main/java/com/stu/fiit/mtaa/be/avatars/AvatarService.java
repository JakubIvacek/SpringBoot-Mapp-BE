package com.stu.fiit.mtaa.be.avatars;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

//    @PostConstruct
//    public void saveImagesToDatabase() {
//        try (Stream<Path> paths = Files.walk(Paths.get("src/Avatars"))) {
//            paths.filter(Files::isRegularFile)
//                    .forEach(file -> {
//                        try {
//                            String imageName = file.getFileName().toString();
//                            if (avatarRepository.findAvatarByName(imageName).isEmpty()) {
//                                byte[] imageData = Files.readAllBytes(file);
//
//                                avatarRepository.save(Avatar.builder()
//                                        .image(imageData)
//                                        .name(imageName)
//                                        .build());
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public byte[] getAvatarById(Long id) {
        return avatarRepository.findById(id).orElseThrow().getImage();
    }

    @Transactional
    public byte[] downloadImage(Long id){
        Avatar dbImageData = avatarRepository.findById(id).orElseThrow();
        return dbImageData.getImage();
    }

    public List<byte[]> getAllAvatars() {
        List<Avatar> avatars = avatarRepository.findAll();
        return avatars.stream()
                .map(Avatar::getImage)
                .toList();
    }
}

package com.example.demoEmail.service;

import com.example.demoEmail.entity.Image;
import com.example.demoEmail.entity.User;
import com.example.demoEmail.repository.ImageRepository;
import com.example.demoEmail.repository.UserRepository;
import com.example.demoEmail.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private UserRepository userRepository;
    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }
    public String uploadImage(MultipartFile file,String userId) throws IOException{
        User user = getUser(userId);
        Image imageData = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .user(user).build()
        );
        if(imageData!=null){
            return "File uploaded Successfully" + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) throws IOException{
        Optional<Image> image = imageRepository.findByName(fileName);
        byte[] imageData = ImageUtils.decompressImage(image.get().getImageData());
        return imageData;
    }

    public User getUser(String userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

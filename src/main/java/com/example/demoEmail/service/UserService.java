package com.example.demoEmail.service;

import com.example.demoEmail.dto.request.UserCreationRequest;
import com.example.demoEmail.dto.request.UserUpdateRequest;
import com.example.demoEmail.entity.Image;
import com.example.demoEmail.entity.User;
import com.example.demoEmail.repository.ImageRepository;
import com.example.demoEmail.repository.UserRepository;
import com.example.demoEmail.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/";
    public User createUser(UserCreationRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .dob(request.getDob())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(UserUpdateRequest request) throws IOException{
        User user = getUser(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        MultipartFile file = request.getImageData();
        user.setImageData(ImageUtils.compressImage(file.getBytes()));
        return userRepository.save(user);
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmailPass(String email, String password){
        return userRepository.login(email,password).orElseThrow(() -> new RuntimeException("User not found"));
    }



    public String uploadImage(MultipartFile file, String userId) throws IOException {
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

    public MultipartFile convertToMultipartFile(byte[] fileData, String fileName, String contentType) {
        MultipartFile multipartFile = new MockMultipartFile(
                fileName,                // Tên của file
                fileName,                // Tên của file (truyền hai lần, một là tên field, một là tên thực tế của file)
                contentType,             // Loại nội dung (MIME type)
                fileData                 // Dữ liệu byte
        );
        return multipartFile;
    }

}

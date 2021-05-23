//package com.example.demo.user;
//import java.awt.image.*;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//
//import javax.imageio.ImageIO;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.upload.Upload;
//import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
//import com.vaadin.flow.server.StreamResource;
//@Service
//public class ImageService {
//    
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//	ImageService imageService;
//    
//    public Image generateImage(User user) {
//        Integer id = user.getId();
//        StreamResource sr = new StreamResource("user", () ->  {
//            User attached = userRepository.findWithPropertyPictureAttachedById(id);
//            return new ByteArrayInputStream(attached.getProfilePicture());
//        });
//        sr.setContentType("image/png");
//        Image image = new Image(sr, "profile-picture");
//        return image;
//
//    }
//    public static byte[] getBytesFromFile(String imagePath) throws IOException {
//        File file = new File(imagePath);
//        return Files.readAllBytes(file.toPath());
//    }
//    private void saveProfilePicture(byte[] imageBytes) {
//        user.setProfilePicture(imageBytes);
//        user = userRepository.save(user);
//    }
//
//   
//
//    
//}
package com.example.globalO2O.login.domain.pushnotification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FCMInitializer {
    @Value("${fcm.service-key.path}")
    private String FCM_SERVICE_KEY_PATH;

    @Value("${fcm.project-id}")
    private String FCM_PROJECT_ID;

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource resource = new ClassPathResource(FCM_SERVICE_KEY_PATH);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .setProjectId(FCM_PROJECT_ID)
                .build();

        FirebaseApp.initializeApp(options);
    }
}

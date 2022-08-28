package com.example.globalO2O.login.domain.pushnotification;

import com.example.globalO2O.login.domain.community.CustomTimeString;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FCMService {
    public String send(int requestId, String registrationToken, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                    .setNotification(AndroidNotification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                .build())
                .putData("requestId", Integer.toString(requestId))
                .setToken(registrationToken)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }

    public void sendByList(List<String> tokenList, String title, String body) throws FirebaseMessagingException {
        List<Message> messages = tokenList.stream().map(token -> Message.builder()
                .putData("time", CustomTimeString.getDatetime())
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(token)
                .build()).collect(Collectors.toList());

        BatchResponse response;
        try {
            response = FirebaseMessaging.getInstance().sendAll(messages);

            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(tokenList.get(i));
                    }
                }

                log.error("List of tokens are not valid token : " + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }
}

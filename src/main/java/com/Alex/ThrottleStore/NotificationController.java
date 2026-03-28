package com.Alex.ThrottleStore;


import com.Alex.ThrottleStore.dto.NotificationRequest;
import com.Alex.ThrottleStore.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid  @RequestBody NotificationRequest request){
        if (service.canSend(request)) {
            return ResponseEntity.ok("Notification sent successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. Try again in 60 seconds.");
        }

    }
}

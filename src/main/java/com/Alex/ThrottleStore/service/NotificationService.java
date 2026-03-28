package com.Alex.ThrottleStore.service;


import com.Alex.ThrottleStore.dto.NotificationRequest;
import com.Alex.ThrottleStore.entity.NotificationLog;
import com.Alex.ThrottleStore.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    public boolean canSend(NotificationRequest request){
        String email  = request.getRecipient();
        LocalDateTime now = LocalDateTime.now();

        Optional<NotificationLog> logEntry = notificationRepository.findByRecipient(email);
        if (logEntry.isEmpty()) {
            notificationRepository.save(NotificationLog.builder()
                    .recipient(email)
                    .lastSentAt(now)
                    .build()
            );
            return true;
        }

        NotificationLog existingLog = logEntry.get();
        long secondsPassed = Duration.between(existingLog.getLastSentAt(), now).toSeconds();
        if (secondsPassed < 60) {
            return false;
        }
        existingLog.setLastSentAt(now);
        notificationRepository.save(existingLog);
        return true;
    }

}

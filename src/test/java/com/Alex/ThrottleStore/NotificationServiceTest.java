package com.Alex.ThrottleStore;

import com.Alex.ThrottleStore.dto.NotificationRequest;
import com.Alex.ThrottleStore.repo.NotificationRepository;
import com.Alex.ThrottleStore.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class NotificationServiceTest {
    @Autowired
    private NotificationService service;

    @Autowired
    private NotificationRepository repository;

    @BeforeEach
    void setup(){
        // clear the DB so that tests don't interfere with each other
        repository.deleteAll();
    }


    @Test
    void whenSendTwiceRapidly_thenSecondAttemptFails(){
        NotificationRequest request = new NotificationRequest("alex@gmail.com", "Hello!");
        boolean firstAttempt = service.canSend(request);
        assertThat(firstAttempt).isTrue();
        var savedLog = repository.findByRecipient("alex@gmail.com");
        assertThat(savedLog).isPresent();
        //Since the save happened a few milliseconds ago, it should be slightly before or exactly equal to "Now."
        assertThat(savedLog.get().getLastSentAt()).isBeforeOrEqualTo(LocalDateTime.now());

        boolean secondAttempt = service.canSend(request);
        assertThat(secondAttempt).isFalse();
    }
}

package com.Alex.ThrottleStore.repo;

import com.Alex.ThrottleStore.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationLog, Long> {
    Optional<NotificationLog> findByRecipient(String recipient);


}

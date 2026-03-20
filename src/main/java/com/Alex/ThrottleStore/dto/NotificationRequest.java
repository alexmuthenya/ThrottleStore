package com.Alex.ThrottleStore.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Recipient cannot be empty" )
    private String recipient;

    @NotBlank(message = "Message cannot be empty")
    private String message;
}

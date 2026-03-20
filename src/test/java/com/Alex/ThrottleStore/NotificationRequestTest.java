package com.Alex.ThrottleStore;

import com.Alex.ThrottleStore.dto.NotificationRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


   public class NotificationRequestTest {
   private Validator validator;

   @BeforeEach
   void setup(){
   ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
   validator = factory.getValidator();
}

   @Test
   void whenEmailIsInvalid_thenValidationFails(){
   NotificationRequest request = new NotificationRequest("not an email", "Hello!");
   var violations = validator.validate(request);
   assertThat(violations).isNotEmpty();
   assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid email format");
}
   @Test
   void whenRequestIsValid_thenNoViolations(){
      NotificationRequest request = new NotificationRequest("alex@gmail.com", "Good morning!");
      var violations = validator.validate(request);
      assertThat(violations).isEmpty();

   }
   @Test
   void whenMessageIsBlank_thenValidationFails(){
      NotificationRequest request =new NotificationRequest("alex@gmail.com", "");
      var violations = validator.validate(request);
      assertThat(violations).isNotEmpty();
      assertThat(violations.iterator().next().getMessage()).isEqualTo("Message cannot be empty");
   }

}

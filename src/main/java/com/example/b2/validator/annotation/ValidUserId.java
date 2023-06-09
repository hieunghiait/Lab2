package com.example.b2.validator.annotation;

import jakarta.validation.Payload;

public @interface ValidUserId {
    String message() default "Invalid User ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

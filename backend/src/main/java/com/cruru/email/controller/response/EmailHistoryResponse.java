package com.cruru.email.controller.response;

import com.cruru.email.domain.EmailStatus;
import java.time.LocalDateTime;

public record EmailHistoryResponse(
        String subject,
        String content,
        LocalDateTime createdDate,
        EmailStatus status
) {

}

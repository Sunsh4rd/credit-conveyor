package com.sunshard.dossier.service.impl;

import com.sunshard.dossier.exception.AttachmentNotCreatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Autowired
    EmailServiceImpl emailService;

    @Test
    void sendMessage() {

    }

    @Test
    void sendMessageWithAttachment() {
        assertThrows(AttachmentNotCreatedException.class,
                () -> emailService.sendMessageWithAttachment(
                        "kap.2000@yandex.ru", "test", "test", "docs90"
                )
        );
    }
}
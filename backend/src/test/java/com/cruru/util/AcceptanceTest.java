package com.cruru.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.cruru.auth.service.AuthService;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import jakarta.mail.internet.MimeMessage;
import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AcceptanceTest {

    private static final Clock FIXED_TIME = LocalDateFixture.fixedClock();

    protected Member defaultMember;
    protected Club defaultClub;
    protected String token;

    @LocalServerPort
    private int port;
    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private DbCleaner dbCleaner;
    @SpyBean
    private Clock clock;
    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void createDefaultLoginMember() {
        dbCleaner.truncateEveryTable();
        defaultMember = memberRepository.save(MemberFixture.ADMIN);
        defaultClub = clubRepository.save(ClubFixture.create(defaultMember));
        token = authService.createAccessToken(defaultMember.getEmail(), defaultMember.getRole()).getToken();
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setClock() {
        doReturn(Instant.now(FIXED_TIME))
                .when(clock)
                .instant();
    }

    @BeforeEach
    void setJavaMailSender() {
        doReturn(mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        doNothing()
                .when(javaMailSender).send(any(MimeMessage.class));
    }
}

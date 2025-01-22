package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Answer;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CSV 파일 쓰기 서비스 테스트")
class CsvExportServiceTest extends ServiceTest {

    @Autowired
    private CsvExportService csvExportService;
    @Autowired
    private ApplyFormRepository applyFormRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("빈 데이터일 경우 CSV 헤더만 생성되는지 테스트")
    void exportApplicantsToCsv_emptyData() {
        long nonExistentApplyFormId = 999L;

        ByteArrayInputStream csvStream = csvExportService.exportApplicantsToCsv(nonExistentApplyFormId);
        String csvText = toStringWithoutBom(csvStream);

        assertThat(csvText).contains("이름,이메일,전화번호,제출일시");
    }

    @Test
    @DisplayName("질문 2개, 지원자 1명, 답변 2개 정상 생성 시 CSV 내용 확인")
    void exportApplicantsToCsv_singleApplicant() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(defaultDashboard));
        Process process = processRepository.save(ProcessFixture.applyType(defaultDashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingRush(process));

        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        Answer answer1 = answerRepository.save(AnswerFixture.first(question1, applicant));
        Answer answer2 = answerRepository.save(AnswerFixture.second(question2, applicant));

        // when
        ByteArrayInputStream csvStream = csvExportService.exportApplicantsToCsv(applyForm.getId());
        String csvText = toStringWithoutBom(csvStream);

        // then
        String expectedHeader = String.join(",",
                "이름", "이메일", "전화번호", "제출일시",
                question1.getContent(), question2.getContent()
        );

        String expectedDataRow = String.join(",",
                applicant.getName(),
                applicant.getEmail(),
                applicant.getPhone(),
                applicant.getCreatedDate().toString(),
                answer1.getContent(),
                answer2.getContent()
        );
        Assertions.assertAll(
                () -> assertThat(csvText).contains(expectedHeader),
                () -> assertThat(csvText).contains(expectedDataRow)
        );
    }

    private String toStringWithoutBom(ByteArrayInputStream stream) {
        String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        if (text.startsWith("\uFEFF")) {
            return text.substring(1);
        }
        return text;
    }
}

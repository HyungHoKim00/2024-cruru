package com.cruru.question.domain;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.auth.util.SecureResource;
import com.cruru.member.domain.Member;
import com.cruru.question.exception.QuestionDescriptionLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Question implements SecureResource {

    private static final int QUESTION_DESCRIPTION_MAX_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private String content;

    private String description;

    private Integer sequence;

    private boolean required;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

    public Question(
            QuestionType questionType,
            String content,
            String description,
            Integer sequence,
            Boolean required,
            ApplyForm applyForm
    ) {
        validateDescriptionLength(description);
        this.questionType = questionType;
        this.content = content;
        this.description = description;
        this.sequence = sequence;
        this.required = required;
        this.applyForm = applyForm;
    }

    private void validateDescriptionLength(String description) {
        if (description != null && description.length() > QUESTION_DESCRIPTION_MAX_LENGTH) {
            throw new QuestionDescriptionLengthException(QUESTION_DESCRIPTION_MAX_LENGTH, description.length());
        }
    }

    public boolean hasChoice() {
        return questionType.hasChoice();
    }

    public boolean isShortAnswer() {
        return questionType == QuestionType.SHORT_ANSWER;
    }

    public boolean isLongAnswer() {
        return questionType == QuestionType.LONG_ANSWER;
    }

    @Override
    public boolean isAuthorizedBy(Member member) {
        return applyForm.isAuthorizedBy(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionType=" + questionType +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", sequence=" + sequence +
                ", applyForm=" + applyForm +
                '}';
    }
}

/* eslint-disable @typescript-eslint/no-explicit-any */
import Button from '@components/_common/atoms/Button';
import InputField from '@components/_common/molecules/InputField';
import CustomQuestion from '@components/recruitmentPost/CustomQuestion';
import { FormEventHandler, useEffect, useState } from 'react';

import { validateEmail, validateName, validatePhoneNumber } from '@domain/validations/apply';

import { ApplicantData, ApplyRequestBody, Question } from '@customTypes/apply';
import { applyMutations, applyQueries } from '@hooks/apply';
import useForm from '@hooks/utils/useForm';
import { useParams } from 'react-router-dom';

import CheckboxLabelField from '@components/_common/molecules/CheckboxLabelField';
import { useToast } from '@contexts/ToastContext';
import { useApplyAnswer } from '@contexts/ApplyAnswerContext';

import C from '../style';
import S from './style';

interface ApplyFormProps {
  questions: Question[];
  isClosed: boolean;
}

export default function ApplyForm({ questions, isClosed }: ApplyFormProps) {
  const { applyFormId } = useParams<{ applyFormId: string }>() as { applyFormId: string };

  const { data: recruitmentPost } = applyQueries.useGetRecruitmentPost({ applyFormId: applyFormId ?? '' });

  const { initialValues, baseInfoHandlers, resetStorage, answers, changeHandler, isRequiredFieldsIncomplete } =
    useApplyAnswer();
  const {
    formData: applicant,
    register,
    errors,
    hasErrors,
  } = useForm<ApplicantData>({
    initialValues,
  });

  const { mutate: apply } = applyMutations.useApply(applyFormId, recruitmentPost?.title ?? '', resetStorage);

  const [personalDataCollection, setPersonalDataCollection] = useState(false);

  const { error } = useToast();

  const questionCount = questions.length + 4;

  const handleSubmit: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    if (isRequiredFieldsIncomplete()) {
      return error('모든 필수 항목에 응답해 주세요.');
    }

    if (hasErrors) {
      return error('지원자 정보를 확인해주세요.');
    }

    if (!personalDataCollection) {
      return error('개인정보 수집 및 이용 동의에 체크해주세요.');
    }

    if (window.confirm('제출 후엔 수정이나 재열람이 불가능하니 신중하게 확인 후 제출하세요.')) {
      apply({
        body: {
          applicant: {
            ...applicant,
            phone: applicant.phone,
          },
          answers: Object.entries(answers).map(([questionId, answer]) => ({
            questionId,
            replies: [...answer],
          })),
          personalDataCollection,
        } as ApplyRequestBody,
      });
    }
  };

  const handlePersonalDataCollection = (checked: boolean) => {
    setPersonalDataCollection(checked);
  };

  return (
    <C.ContentContainer>
      <S.Form onSubmit={handleSubmit}>
        <NameInput
          questionCount={questionCount}
          register={register}
          value={applicant.name}
          error={errors.name}
          baseInfoHandlers={baseInfoHandlers}
        />
        <EmailInput
          questionCount={questionCount}
          register={register}
          value={applicant.email}
          error={errors.email}
          baseInfoHandlers={baseInfoHandlers}
        />
        <PhoneInput
          questionCount={questionCount}
          register={register}
          value={applicant.phone}
          error={errors.phone}
          baseInfoHandlers={baseInfoHandlers}
        />

        {questions.map((question, index) => (
          <S.AriaCustomQuestion
            key={question.questionId}
            aria-label={`총 ${questionCount}의 입력 중 ${index + 4}번째 질문입니다.`}
          >
            <CustomQuestion
              question={question}
              value={answers[question.questionId]}
              isLengthVisible
              onChange={changeHandler[question.type]}
            />
          </S.AriaCustomQuestion>
        ))}

        <S.Divider />

        <S.AriaCustomQuestion aria-label="마지막 질문입니다.">
          <CheckboxLabelField
            options={[
              {
                optionLabel: '개인정보 수집 및 이용 동의',
                isChecked: personalDataCollection,
                onToggle: handlePersonalDataCollection,
              },
            ]}
            label="아래 항목을 확인해주세요."
            description="입력하신 정보는 지원자 식별, 본인 확인, 모집 전형 진행을 위해 사용됩니다."
            required
          />
        </S.AriaCustomQuestion>

        <C.ButtonContainer>
          <Button
            type="submit"
            color="primary"
            size="fillContainer"
            disabled={isClosed}
          >
            제출하기
          </Button>
        </C.ButtonContainer>
      </S.Form>
    </C.ContentContainer>
  );
}

interface BaseInputProps {
  questionCount: number;
  register: any;
  baseInfoHandlers: {
    handleName: (value: string) => void;
    handleEmail: (value: string) => void;
    handlePhone: (value: string) => void;
  };
}

interface NameInputProps extends BaseInputProps {
  value: string;
  error: string;
}

interface EmailInputProps extends BaseInputProps {
  value: string;
  error: string;
}

interface PhoneInputProps extends BaseInputProps {
  value: string;
  error: string;
}

function NameInput({ questionCount, register, value, error, baseInfoHandlers }: NameInputProps) {
  const nameRegister = register('name', {
    validate: { onBlur: validateName.onBlur, onChange: validateName.onChange },
  });

  useEffect(() => {
    if (error) baseInfoHandlers.handleName('');
    else baseInfoHandlers.handleName(value);
  }, [error, value]);

  return (
    <S.AriaCustomQuestion aria-label={`총 ${questionCount}의 입력 중 1번째 입력입니다.`}>
      <InputField
        {...nameRegister}
        name="name"
        label="이름"
        placeholder="이름을 입력해 주세요."
        maxLength={32}
        required
        value={value}
      />
    </S.AriaCustomQuestion>
  );
}

function EmailInput({ questionCount, register, value, error, baseInfoHandlers }: EmailInputProps) {
  const emailRegister = register('email', {
    validate: { onBlur: validateEmail.onBlur },
  });

  useEffect(() => {
    if (error) baseInfoHandlers.handleEmail('');
    else baseInfoHandlers.handleEmail(value);
  }, [error, value]);

  return (
    <S.AriaCustomQuestion aria-label={`총 ${questionCount}의 입력 중 2번째 입력입니다.`}>
      <InputField
        {...emailRegister}
        label="이메일"
        placeholder="지원 결과를 안내받을 이메일 주소를 입력해 주세요."
        required
        value={value}
      />
    </S.AriaCustomQuestion>
  );
}

function PhoneInput({ questionCount, register, value, error, baseInfoHandlers }: PhoneInputProps) {
  const phoneRegister = register('phone', {
    validate: {
      onBlur: validatePhoneNumber.onBlur,
      onChange: validatePhoneNumber.onChange,
    },
  });

  useEffect(() => {
    if (error) baseInfoHandlers.handlePhone('');
    else baseInfoHandlers.handlePhone(value);
  }, [error, value]);

  return (
    <S.AriaCustomQuestion aria-label={`총 ${questionCount}의 입력 중 3번째 입력입니다.`}>
      <InputField
        {...phoneRegister}
        inputMode="numeric"
        label="전화 번호"
        placeholder="번호만 입력해 주세요."
        maxLength={11}
        required
        value={value}
      />
    </S.AriaCustomQuestion>
  );
}

# 서비스 이름

### 크루루 (cruru)

# 크루루는 이런 서비스에요

## 주제

복잡한 리크루팅 과정을 간소화하는 맞춤형 리크루팅 관리 솔루션

## 설명

서비스 ‘크루루’는 대학생 연합 동아리를 위한 ATS(지원자 추적 시스템)입니다.  모집 공고 관리, 지원자 목록 관리, 지원 항목 커스터마이징 등을 제공합니다. 해당 서비스를 통해 소규모 리크루팅 프로세스를 효율적으로 관리할 수 있습니다.

# 저의 역할

백엔드 및 인프라

## 주요 기여

이메일 비동기 전송 구현 - [정리 글](https://blog.cruru.kr/docs/backend/%EC%9D%B4%EB%A9%94%EC%9D%BC-%EB%B0%9C%EC%86%A1-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%A0%81%EC%9A%A9%EA%B8%B0-fff1e50d803f813bae2eea918fcf0302/)   
API 문서화 - [정리 글](https://blog.cruru.kr/docs/backend/restdocs-%EB%8F%84%EC%9E%85%ED%95%98%EA%B8%B0-fff1e50d803f8139a486c56e6c397adb/)   
k6 부하 테스트 및 서버 성능 측정 - [정리 글](https://blog.cruru.kr/docs/infra/%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8%EB%A1%9C-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%ED%95%98%EA%B8%B0-60bb8cbedc2743179befc3af6eb42037/)  

## PR 목록

### Feature
[테스트용 더미 데이터 생성](https://github.com/woowacourse-teams/2024-cruru/pull/50)   
[지원자 기본 정보 조회 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/63)   
[지원자 상세 정보 조회 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/72)   
[지원자 불합격 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/116)   
[불합격자 재검토 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/451)   
[지원폼 질문 수정 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/548)   
[이메일 발송 기능 추가](https://github.com/woowacourse-teams/2024-cruru/pull/644)   
[대시보드 삭제 API 생성](https://github.com/woowacourse-teams/2024-cruru/pull/749)   
[평가 삭제 API 추가](https://github.com/woowacourse-teams/2024-cruru/pull/950)   
[평가 테이블에 평가자 이름 필드 추가 및 API 변경](https://github.com/woowacourse-teams/2024-cruru/pull/958)   
[이메일 발송 내역 조회 API 추가](https://github.com/woowacourse-teams/2024-cruru/pull/974)   
[이메일 템플릿 기능 추가](https://github.com/woowacourse-teams/2024-cruru/pull/988)   

### Refactor
[코드 포맷팅](https://github.com/woowacourse-teams/2024-cruru/pull/48)   
[코드 일괄 변경](https://github.com/woowacourse-teams/2024-cruru/pull/87)   
[예외 처리 방식 변경](https://github.com/woowacourse-teams/2024-cruru/pull/103)   
[지원자 상세 정보 API 수정](https://github.com/woowacourse-teams/2024-cruru/pull/114)   
[지원자 기본 조회 API 수정](https://github.com/woowacourse-teams/2024-cruru/pull/125)   
[기본 데이터 수정](https://github.com/woowacourse-teams/2024-cruru/pull/143)   
[사용되지 않는 필드 제거](https://github.com/woowacourse-teams/2024-cruru/pull/306)   
[의존성 제거](https://github.com/woowacourse-teams/2024-cruru/pull/433)   
[process 도메인에 type 필드 추가](https://github.com/woowacourse-teams/2024-cruru/pull/486)   
[question 도메인의 description 필드 제거](https://github.com/woowacourse-teams/2024-cruru/pull/517)   
[applicant 도메인의 state 필드 수정](https://github.com/woowacourse-teams/2024-cruru/pull/518)   
[지원자 이름 및 전화번호 검증 추가](https://github.com/woowacourse-teams/2024-cruru/pull/524)   
[프로세스 목록 조회 API 응답에 평균 점수 필드 추가](https://github.com/woowacourse-teams/2024-cruru/pull/553)   
[지원자 정보 응답 DTO에 불합격 여부 필드 추가](https://github.com/woowacourse-teams/2024-cruru/pull/554)   
[지원서 제출 시 응답 존재 유무 검증 추가](https://github.com/woowacourse-teams/2024-cruru/pull/556)   
[지원서 제출 시 모집 기간 검증 추가](https://github.com/woowacourse-teams/2024-cruru/pull/576)   
[지원자가 선택 질문에 응답하지 않은 경우, 빈 문자열을 저장하도록 수정](https://github.com/woowacourse-teams/2024-cruru/pull/611)   
[지원 폼의 url 필드 제거 및 응답에서 쓰인 postId와 url을 applyFormId로 변경](https://github.com/woowacourse-teams/2024-cruru/pull/641)   
[불합격, 불합격 취소 API에서 사용되지 않는 응답 필드 제거](https://github.com/woowacourse-teams/2024-cruru/pull/648)   
[지원 폼 제출 시 필수 질문 응답 여부 검증 로직 변경](https://github.com/woowacourse-teams/2024-cruru/pull/654)   
[이메일 전송 로직 중 지원자 일괄 조회 기능 개선](https://github.com/woowacourse-teams/2024-cruru/pull/869)   
[공고 본문 db 저장 타입 변경](https://github.com/woowacourse-teams/2024-cruru/pull/1026)   

### Fix
[지원자 단계 변경 API 오류 수정](https://github.com/woowacourse-teams/2024-cruru/pull/45)   
[평가 조회 오류 수정](https://github.com/woowacourse-teams/2024-cruru/pull/179)   
[error 로그에 stack trace 제거 및 로컬 환경에서 로그가 저장되지 않도록 변경](https://github.com/woowacourse-teams/2024-cruru/pull/282)   
[대시보드 생성 API 응답 필드 수정](https://github.com/woowacourse-teams/2024-cruru/pull/375)   
[대시보드 생성 시 첫 프로세스의 sequence 오류 수정](https://github.com/woowacourse-teams/2024-cruru/pull/426)   
[중첩 DTO 검증 추가](https://github.com/woowacourse-teams/2024-cruru/pull/512)   
[지원 폼 업데이트 시 모집 기간을 검증하도록 수정](https://github.com/woowacourse-teams/2024-cruru/pull/582)   
[지원 날짜가 지원 시작 날짜인 경우 지원이 불가능한 오류 수정](https://github.com/woowacourse-teams/2024-cruru/pull/583)   
[지원 시작 이후 모집 공고의 질문을 수정할 수 없도록 변경](https://github.com/woowacourse-teams/2024-cruru/pull/628)   
[예외 처리 과정에서 NPE가 발생하지 않도록 수정](https://github.com/woowacourse-teams/2024-cruru/pull/753)   
[사전 데이터 수정](https://github.com/woowacourse-teams/2024-cruru/pull/783)   
[지원자 기본 정보 조회 시 평가 점수와 갯수가 모든 프로세스에 대해 나오는 버그 수정](https://github.com/woowacourse-teams/2024-cruru/pull/902)   
[이메일 인증 시 이미 가입된 이메일은 예외를 던지도록 변경](https://github.com/woowacourse-teams/2024-cruru/pull/906)   


# 💻 개발자

|  ![아르](https://github.com/user-attachments/assets/2f63c5ab-43bb-417b-92bf-73fd761208a9)  |    ![러기](https://github.com/user-attachments/assets/f2c8ff64-1a83-466c-851a-ab14cd5530bc)|   ![렛서](https://github.com/user-attachments/assets/ff5d9e17-16d6-42fc-8754-c65554313e4e) |  ![냥인](https://github.com/user-attachments/assets/4b20cc25-7104-413c-b89e-f22c34a8d0c9)  |  ![러쉬](https://github.com/user-attachments/assets/86225998-321c-4a11-9c30-2abff1b1c3a1)  |   ![명오](https://github.com/user-attachments/assets/5316b64b-bc98-446b-b55f-8fa014dbceaa) |  ![도비](https://github.com/user-attachments/assets/777f53ac-07cf-43e3-8ebb-f11ae1dc8520)  |   ![초코칩](https://github.com/user-attachments/assets/dcbd7b64-0ee9-434e-936e-98bf4a36a03d) |
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| **FE** | **FE** | **FE** | **BE** | **BE** | **BE** | **BE** | **BE** |
|[아르](https://github.com/seongjinme)| [러기](https://github.com/lurgi) | [렛서](https://github.com/llqqssttyy) | [냥인](https://github.com/cutehumanS2) | [러쉬](https://github.com/xogns1514) | [명오](https://github.com/HyungHoKim00) | [도비](https://github.com/Dobby-Kim) | [초코칩](https://github.com/Chocochip101) |   

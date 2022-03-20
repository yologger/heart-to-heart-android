# Heart to Heart (Android)

## Introduction
`Heart to Heart`는 Facebook, Instagram과 유사한 SNS 어플리케이션입니다.

## Prerequisite
`Heart to Heart`은 서버 환경 구성이 필요합니다. 서버 어플리케이션은 [이 곳](https://github.com/yologger/heart-to-heart-api)에서 확인할 수 있습니다.

## Screenshots

`이메일 인증`, `회원가입`

<img src="/imgs/email_verification_code.gif" width="300">

<img src="/imgs/join.gif" width="300">

`로그인`

<img src="/imgs/login.gif" width="300">

`Infinite Scrolling`, `Refresh`

<img src="/imgs/infinite.gif" width="300">

`글 작성`

<img src="/imgs/create_post.gif" width="300">

`아바타 이미지 업데이트`

<img src="/imgs/update_avatar.gif" width="300">

`팔로잉`, `팔로우` (UI 구현, 기능 미구현)

<img src="/imgs/follow.gif" width="300">

`로그아웃`

<img src="/imgs/logout.gif" width="300">

## 아키텍처
* `Gradle 멀티모듈` 기반 `Layered Architectrue`
* `Clean Architecture`
* `MVVM` 

## 기술 스택
### 공통
* Kotlin
* ACC
    - Data Binding 
    - ViewModel 
    - LiveData
* Android KTX
* ReactiveX
    * RxJava
    * RxKotlin
    * RxAndroid
* Hilt
* Retrofit2
* Glide
* Slider Glide
* TedImagePicker

### 테스트 환경
- Truth
- JUnit 4
- Androidx Test
- Mockito
- MockWebServer

## Todo List
- [x] 이메일 인증 기반 회원가입 구현
- [x] OAuth2, JWT(Access Token, Refresh Token) 기반 로그인 구현
- [x] `Infinite Scrolling` 구현
- [ ] 팔로잉, 팔로우 구현
- [ ] 비밀번호 찾기 구현
- [ ] 비밀번호 변경하기 구현
- [ ] MVI 아키텍처 패턴으로 이전
    - [ ] Coroutine `Flow`, `Channel`을 통한 단방향 데이터 플로우 구현
- [ ] Google AdMob 추가
- [ ] 다중 클릭 이슈 제거 (RxBinding)
- [ ] 계측 테스트 환경에서 `Hilt`를 통한 의존성 주입
- [ ] 테스트 환경 - `GitHub Actions`, `Firebase App Distribution`을 통한 CI/CD pipeline 구축  
- [ ] 운영 환경 - `GitHub Actions`, `Google Play Console`을 통한 CI/CD pipeline 구축
- [ ] 다국어 지원
- [ ] 다크 테마 구현
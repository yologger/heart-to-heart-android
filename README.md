# Heart to Heart (Android)

## Introduction
`Heart to Heart`는 Facebook, Instagram과 유사한 SNS 어플리케이션입니다. 이 앱은 서버 환경 구성이 필요하며, [이 곳](https://github.com/yologger/heart-to-heart-api)에서 다운받을 수 있습니다.

## Download
`Heart to Heart`를 구글 플레이스토어에서 다운받을 수 있습니다.

<br />
<a href='https://play.google.com/store/apps/details?id=com.yologger.heart_to_heart'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="280"/></a>

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
* Truth
* JUnit 4
* Androidx Test
* Mockito
* MockWebServer

## 아키텍처
* `Gradle 멀티 모듈` 기반 `Layered Architectrue`
* `Clean Architecture`
* `MVVM`

## DevOps
### 테스트 환경
<img src="/imgs/1.png">

### 운영 환경
<img src="/imgs/2.png">

## Todo
- [x] 이메일 인증 기반 회원가입 구현
- [x] OAuth2, JWT(Access Token, Refresh Token) 기반 로그인 구현
- [x] `Infinite Scrolling` 구현
- [x] 내 글 보기 구현
- [x] 글 삭제 구현  
- [ ] 팔로잉, 팔로우 구현
- [ ] 비밀번호 찾기 구현
- [ ] 비밀번호 변경하기 구현
- [ ] MVI 아키텍처 패턴으로 이전
    - [ ] Coroutine `Flow`, `Channel`을 통한 단방향 데이터 플로우 구현
- [ ] Google AdMob 추가
- [ ] 다중 클릭 이슈 제거 (RxBinding)
- [ ] 계측 테스트 환경에서 `Hilt`를 통한 의존성 주입
- [x] 테스트 환경 - `GitHub Actions`, `Firebase App Distribution`을 통한 CI/CD pipeline 구축  
- [x] 운영 환경 - `GitHub Actions`, `Google Play Console`을 통한 CI/CD pipeline 구축
- [ ] 다국어 지원 적용
- [ ] 다크 테마 지원
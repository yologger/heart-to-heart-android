# Heart to Heart (Android)

## Introduction
`Heart to Heart`는 SNS 어플리케이션입니다. 이 앱은 서버 환경 구성이 필요하며, [이 곳](https://github.com/yologger/heart-to-heart-api)에서 소스 코드를 확인할 수 있습니다.

## Download
`Heart to Heart`를 구글 플레이스토어에서 다운받을 수 있습니다.

<br />
<a href='https://play.google.com/store/apps/details?id=com.yologger.heart_to_heart'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="280"/></a>

## 기능 구현
- [x] Gmail을 통한 이메일 코드 전송 및 인증
- [x] 회원 가입 구현
- [x] 토큰 기반 로그인 구현 (`Retrofit2`)
- [x] 토큰 갱신 구현(`OkHttp3 Interceptor`)
- [x] 로그아웃 구현
- [x] 회원 탈퇴 구현
- [x] 아바타 이미지 업로드 구현
- [x] 사용자 차단 및 해제 구현
- [x] 사용자 신고 구현
- [x] 게시글 작성 구현
- [x] 이미지 업로드 구현 (`TedImagePicker`, `Glide`, `Slider Glide`)
- [x] 게시글 조회 구현
- [x] 게시글 삭제 구현
- [x] Infinite Scrolling 구현
- [x] `JUnit 5`, `Mockito`, `Truth`를 통한 테스트 코드 구현 
- [x] `Hilt`를 통한 의존성 주입 구축
- [x] `RxJava`, `RxAndroid`, `RxKotlin`을 통한 논블로킹/비동기 구현  
- [x] 내 글 보기 구현
- [x] 글 삭제 구현
- [x] MVVM, Clean Architecture 적용
- [x] `Gradle` 멀티 모듈을 통한 Layered Architecture 적용
- [x] Staging 환경 - `GitHub Actions`, `Firebase App Distribution`을 통한 CI/CD 구축
- [x] Production 환경 - `GitHub Actions`, `Google Play Console`을 통한 구글 플레이스토어 배포 자동화 구축

### Staging 환경
<img src="/imgs/1.png">

### Production 환경
<img src="/imgs/2.png">
MFA(Multi Factor Authentication) 데모 서비스
---
`auth-service`: 사용자/인증을 담당하는 서버로 다음 기능을 수행한다.
* 사용자 생성
* 사용자 ID/PW 검증
* OTP 검증 담당

`business-service`: 로그인 엔드포인트를 제공하는 서비스

![image](https://github.com/user-attachments/assets/6bba418b-6075-45ae-96cf-f7bd13c3c0e3)

`auth-service` 테스트 시나리오
---
**1. 새로운 사용자 등록**
* Aspect에 의해 비밀번호가 BCrypt 암호화 되어 저장
</br>

```
POST /api/v1/users
Content-Type: application/json

{
  "userId": "jaimemin",
  "password": "1234"
}
```

**2. 사용자 인증**
* 사용자 인증 성공 시 OTP 발급
* OTP 값은 6자리 숫자
</br>

```
POST /api/v1/users/auth
Content-Type: application/json

{
  "userId": "jaimemin",
  "password": "1234"
}
```

**3. OTP 인증**
* OTP 유효성 검증
</br>

```
POST /api/v1/otp/check
Content-Type: application/json

{
  "userId": "jaimemin",
  "otpCode": "123456"
}
```

`business-service` 테스트 시나리오
---
**0. 사전 준비**
* `auth-service` 서버 기동
* `auth-service`의 `1번 새로운 사용자 등록` 수행

**1. 사용자 아이디 및 패스워드 검증**
* username과 password를 헤더에 담아 `http://localhost:8080/login` 호출
* 호출 성공 시 DB의 OTP 테이블에 값이 생성된 것을 확인 가능

```
$ curl -H "username:jaimemin" -H "password:1234" http://localhost:8080/login
```

**2. 사용자 아이디 및 OTP 검증**
* DB에 있는 OTP와 사용자 아이디를 헤더에 담아 다시 `http://localhost:8080/login` 호출
* 응답으로 반환되는 `Authorization` 내 JWT 확인

```
$ curl -H "username:jaimemin" -H "otpCode:123456" http://localhost:8080/login
```

**3. `/api/v1/test` 엔드포인트 호출**
* 2번 과정에서 전달받은 `Authorization`을 헤더에 담아 `/api/v1/test` 엔드포인트 호출
* `TEST SUCCEEDED`를 응답으로 받으면 성공

```
$ curl -H "Authorization:eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImphaW1lbWluIn0.I4ePHpB6CsPx-Z2WxG-qc8VNoiGrRDQCL25-yOjx69I" http://localhost:8080/api/v1/test
```

# 일정 관리 앱 만들기

## 프로젝트 소개
- 모든 테이블은 고유 식별자(ID)를 가집니다.
- 3 Layer Architecture에 따라 각 Layer의 목적에 맞게 개발합니다.
- CRUD 필수 기능은 모두 데이터베이스 연결 및 JPA를 사용해서 개발합니다.
- JDBC와 Spring Security는 사용하지 않습니다.
- 인증/인가 절차는 JWT를 활용하여 개발
- JPA의 연관관계는 양방향으로 구현합니다.

## 프로젝트 요구사항(설계)
Lv1. API 명세 및 ERD 작성<br>

1. API 명세서

| 기능                   | Method | URL                          | request                                                  | response                                                |
|------------------------|--------|------------------------------|----------------------------------------------------------|--------------------------------------------------------|
| 회원 가입              | POST   | /api/user/signup             | { "email": "{email}", "username": "{name}", "password": "{pw}" } | { "id": {id}, "email": "{email}", "username": "{name}", "password": "{pw}" } |
| 로그인                 | GET    | /api/user/signin             | { "email": "{email}", "username": "{name}", "password": "{pw}" } | email                                                 |
| 아이디 중복 여부 체크  | GET    | /api/user/{email}           | Param: email                                           |                                                        |
| 회원 정보 수정        | PUT    | /api/user/modify             | { "email": "{email}", "username": "{name}", "password": "{pw}" } | { "id": {id}, "email": "{email}", "username": "{name}", "password": "{pw}" } |
| 회원 정보 삭제        | DELETE | /api/user/delete             | Cookie에 저장된 JWT: id                               |                                                        |
| 일정 등록             | POST   | /api/schedule/submit         | { "date": "{schedule date}", "title": "{title}", "contents": "{contents}" } | { "id": {id}, "date": "{schedule date}", "title": "{title}", "contents": "{contents}", "updatedAt": "{updatedAt}" } |
| 일정 전체 조회        | GET    | /api/schedule/list/{page}    | Param:                                                   | { "id": {id}, "date": "{schedule date}", "title": "{title}", "contents": "{contents}", "updatedAt": "{updatedAt}" } |
| 특정 일정 조회        | GET    | /api/schedule/userSchedule/{id}      | Param:                                                   | { "id": {id}, "date": "{schedule date}", "title": "{title}", "contents": "{contents}", "updatedAt": "{updatedAt}" } |
| 일정 수정             | PUT    | /api/schedule/modify/{id}    | Param: , { "date": "{schedule date}", "title": "{title}", "contents": "{contents}" } | { "id": {id}, "date": "{schedule date}", "title": "{title}", "contents": "{contents}", "updatedAt": "{updatedAt}" } |
| 일정 삭제             | DELETE | /api/schedule/delete/{id}     | Param: id                                              |                                                        |
| 댓글 등록             | POST   | /api/comment/submit/{일정id}  | { "comment": "{comment}" }                             | { "id": {id}, "scheduleId": {id}, "comment": "{comment}" } |
| 댓글 전체 조회        | GET    | /api/comment/list/{일정id}    | Param:                                                 | { "id": {id}, "scheduleId": {id}, "comment": "{comment}" } |
| 댓글 수정             | PUT    | /api/comment/modify/{댓글id}  | Param: , { "comment": "{comment}" }                     | { "id": {id}, "scheduleId": {id}, "comment": "{comment}" } |
| 댓글 삭제             | DELETE | /api/comment/delete/{댓글id}  | Param: id                                              |                                                        |

2. ERD
![calendar_diagram.png](img%2Fcalendar_diagram.png)

## 프로젝트 요구사항(구현)

Lv1. 일정 CRUD 구현
1. 일정을 저장, 조회, 수정, 삭제
2. 삭제의 경우
   - 일정을 삭제할 때 일정의 댓글도 함게 삭제
   - 이 때 JPA의 영속성 전이 기능을 활용


Lv2. 댓글 CRUD
1. 기 생성한 일정에 댓글을 남길 수 있음
2. 댓글을 저장, 조회, 수정, 삭제


Lv3. 일정 페이징 조회
1. 일정의 수정일을 기준으로 내림차순 정렬


Lv4. 유저 CRUD
1. 유저를 저장, 조회, 삭제
2. 일정은 이제 작성 유저명 필드 대신 유저 고유 식별자 필드를 가짐
3. 일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있음


Lv5. 다양한 예외처리 적용하기
1. validation을 활용해 다양한 예외처리를 적용


Lv6. 회원가입(JWT)
1. 유저에 비밀번호 필드를 추가
   - 비밀번호는 암호화되어야 함
   - 암호화를 위한 PasswordEncoder를 직접 만들어 사용
2. 유저 최초 생성(회원 가입) 시 JWT를 발급 후 반환


Lv7. 로그인(인증)
1. JWT를 활용해 로그인 기능을 구현 , 필터를 활용해 인증 처리

2. 예외처리
   - 로그인 시 이메일과 비밀번호가 일치하지 않은 경우 401을 반환
   - 토큰이 없는 경우 400을 반환
   - 유효 기간이 만료된 토큰의 경우 401을 반환


Lv8. 권한 확인(인가)
1. 유저에 권한을 추가(관리자, 일반 사용자)
2. 예외 처리
   - 권한이 없는 유저가 일정 수정 및 삭제를 요청했을 때 403을 반환


Lv9. 외부 API 조회
1. 외부 API 연동
   - 일정 생성 시에 날씨 정보를  생성일 기준으로 저장할 수 있음


## 애플리케이션 기능 구현
해당 테스트는 Postman을 사용하여 진행하였습니다.
```json
// 회원가입
http://localhost:8080/api/user/signup

// request
{
"email":"test@example.com",
"username":"홍길동",
"password":"hong1234"
}

// response
{
"id": 1,
"email":"test@example.com",
"username":"홍길동",
"password":"hong1234"
}
```
```json
// 로그인
http://localhost:8080/api/user/signin

// request
{
"email":"test@example.com",
"username":"홍길동",
"password":"hong1234"
}

// response
test@example.com
```
```text
Authorization에 Bear Token을 추가하여 토큰 값을 넣어준다.
```
```json
// 아이디 중복 체크
http://localhost:8080/api/user/test@example.com

// request
{
"email":"test@example.com",
"username":"홍길동",
"password":"hong1234"
}

// response
test@example.com
```
```json
// 회원정보수정
http://localhost:8080/api/user/modify

// request
{
"email":"test1@example.com",
"username":"홍길동",
"password":"hong1234"
}

// response
{
"id": 1,
"email":"test1@example.com",
"username":"홍길동",
"password":"hong1234"
}
```
```json
// 회원정보삭제
http://localhost:8080/api/user/delete
```
```json
// 일정 등록
http://localhost:8080/api/schedule/submit

// request
{
"date":"2024-11-14",
"title":"일정테스트제목",
"contents":"일정테스트내용"
}

// response
{
"id": 1,
"date": "2024-11-14",
"title": "일정테스트제목",
"contents": "일정테스트내용",
"updatedAt": "2024-10-17T02:13:03.543713"
}
```
```json
// 일정 전체 조회
// 1페이지 조회
http://localhost:8080/api/schedule/list/1
```
```json
// 특정 일정 조회
http://localhost:8080/api/schedule/userSchedule/1
```
```json
// 일정 수정
http://localhost:8080/api/schedule/modify/1

// request
{
"date":"2024-11-14",
"title":"일정테스트제목수정",
"contents":"일정테스트내용수정"
}

// response
{
"id": 1,
"date": "2024-11-14",
"title": "일정테스트제목수정",
"contents": "일정테스트내용수정",
"updatedAt": "2024-10-17T02:20:03.543713"
}
```
```json
// 일정 삭제
http://localhost:8080/api/schedule/delete/1
```
```json
// 댓글 등록
http://localhost:8080/api/comment/submit/1

// request
{
"comment":"와우 일정 너무 좋네요!"
}

// response
{
"id": 1,
"scheduleId": 1,
"comment": "와우 일정 너무 좋네요!"
}
```
```json
// 댓글 전체 조회
http://localhost:8080/api/comment/list/1
        
// response
{
"id": 1,
"scheduleId": 1,
"comment": "와우 일정 너무 좋네요!"
}
```
```json
// 댓글 수정
http://localhost:8080/api/comment/modify/1
        
// request
{
"comment":"다시 한번 보려고 찾아왔습니다"
}
        
// response
{
"id": 1,
"scheduleId": 1,
"comment": "다시 한번 보려고 찾아왔습니다"
}
```
```json
// 댓글 삭제
http://localhost:8080/api/comment/delete/1
```




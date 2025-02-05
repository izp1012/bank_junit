#Junit Bank App

### Jpa LocaleDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate    //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate   //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;


```
## CORS
```
CORS (Cross Origin Resource Sharing)
서버와 클라이언트가 동일한 IP주소에서 동작하고 있다면, resource를 제약 없이 서로 공유할 수 있지만, 만약 다른 도메인에 있다면 원칙적으로 어떤 데이터도 주고받을 수 없도록 하는 매커니즘입니다.
```


## SpringBoot + React 연동
아래 링크 참조하여 만들
https://velog.io/@ung6860/React-Spring-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%83%9D%EC%84%B1



### Axios를 이용한 서버 통신
#### 1. src/main/frontend 폴더에서 필요한 모듈 설치 
    npm install http-proxy-middleware --save    
    npm install axios --save
#### 2. setProxy.js 파일을 생성하고, 아래의 코드를 붙여넣기
```js
// src/main/frontend/src/setProxy.js

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',	# 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
    })
  );
};
```
-> 프론트엔드에서 '/api'로 요청을 보내면, 백엔드인 8080포트(=target)로 요청이 도착




# 토비의 스프링 책읽기

토비의 스프링 3.1을 읽고 핵심 내용을 정리한 저장소입니다.

---

## 구조

```
├── chapter01~06/   # 챕터별 핵심 내용 정리 (md)
└── practice/       # 챕터별 예제 및 테스트 코드 (Spring Boot 3.3 + Java 17)
```

---

## 목차

### 1장. 오브젝트와 의존관계

| 항목 | 제목 |
|---|---|
| 1.4 | [제어의 역전 (IoC)](chapter01/1.4_IoC.md) |
| 1.5 | [스프링의 IoC](chapter01/1.5_Spring_IoC.md) |
| 1.6 | [싱글톤 레지스트리와 오브젝트 스코프](chapter01/1.6_Singleton_Registry.md) |
| 1.7 | [의존관계 주입 (DI)](chapter01/1.7_DI.md) |
| 1.8 | [XML을 이용한 설정](chapter01/1.8_XML_Configuration.md) |

---

### 2장. 테스트

| 항목 | 제목 |
|---|---|
| 2.3 | [개발자를 위한 테스팅 프레임워크 JUnit](chapter02/2.3_JUnit.md) |
| 2.4 | [스프링 테스트 적용](chapter02/2.4_Spring_Test.md) |
| 2.5 | [학습 테스트로 배우는 스프링](chapter02/2.5_Learning_Test.md) |

---

### 3장. 템플릿

| 항목 | 제목 |
|---|---|
| 3.2 | [변하는 것과 변하지 않는 것](chapter03/3.2_Template_Strategy.md) |
| 3.3 | [JDBC 전략 패턴의 최적화](chapter03/3.3_JDBC_Strategy_Optimize.md) |
| 3.4 | [컨텍스트와 DI](chapter03/3.4_Context_DI.md) |
| 3.5 | [템플릿과 콜백](chapter03/3.5_Template_Callback.md) |

---

### 5장. 서비스 추상화

| 항목 | 제목 |
|---|---|
| 5.1 | [사용자 레벨 관리 기능 추가](chapter05/5.1_User_Level.md) |
| 5.2 | [트랜잭션 서비스 추상화](chapter05/5.2_Transaction.md) |
| 5.3 | [서비스 추상화와 단일 책임 원칙](chapter05/5.3_Service_Abstraction.md) |
| 5.4 | [메일 서비스 추상화](chapter05/5.4_Mail_Abstraction.md) |

---

### 6장. AOP

| 항목 | 제목 |
|---|---|
| 6.1 | [트랜잭션 코드의 분리](chapter06/6.1_Transaction_Separation.md) |
| 6.2 | [고립된 단위 테스트](chapter06/6.2_Isolated_Unit_Test.md) |
| 6.3 | [다이내믹 프록시와 팩토리 빈](chapter06/6.3_Dynamic_Proxy.md) |
| 6.4 | [스프링의 프록시 팩토리 빈](chapter06/6.4_Spring_ProxyFactoryBean.md) |
| 6.5 | [스프링 AOP](chapter06/6.5_Spring_AOP.md) |
| 6.6 | [트랜잭션 속성](chapter06/6.6_Transaction_Attribute.md) |
| 6.7 | [애노테이션 트랜잭션 속성과 포인트컷](chapter06/6.7_Annotation_Transaction.md) |
| 6.8 | [트랜잭션 지원 테스트](chapter06/6.8_Transaction_Test.md) |

---

## 예제 코드 (practice/)

> Gradle · H2 in-memory DB

### 1장. 오브젝트와 의존관계

| 항목 | 클래스 | 설명 |
|---|---|---|
| 1.4~1.5 | [ConnectionMaker](practice/src/main/java/com/example/practice/chapter01/ConnectionMaker.java) | DB 연결 전략 인터페이스 |
| 1.5 | [DaoFactory](practice/src/main/java/com/example/practice/chapter01/DaoFactory.java) | `@Configuration` / `@Bean` IoC 설정 |
| 1.7 | [UserDao](practice/src/main/java/com/example/practice/chapter01/UserDao.java) | DataSource DI 적용 DAO |
| 테스트 | [UserDaoTest](practice/src/test/java/com/example/practice/chapter01/UserDaoTest.java) | ApplicationContext 이용 테스트 |

### 2장. 테스트

| 항목 | 클래스 | 설명 |
|---|---|---|
| 2.3 | [UserDaoJUnitTest](practice/src/test/java/com/example/practice/chapter02/UserDaoJUnitTest.java) | `@BeforeEach` 픽스처, 예외 테스트 |
| 2.4 | [UserDaoSpringTest](practice/src/test/java/com/example/practice/chapter02/UserDaoSpringTest.java) | `@ExtendWith` 스프링 컨텍스트 공유 |
| 2.5 | [JUnitLearningTest](practice/src/test/java/com/example/practice/chapter02/JUnitLearningTest.java) | JUnit 인스턴스 생성 & 컨텍스트 공유 검증 |

### 3장. 템플릿

| 항목 | 클래스 | 설명 |
|---|---|---|
| 3.2 | [UserDaoWithStrategy](practice/src/main/java/com/example/practice/chapter03/UserDaoWithStrategy.java) | 전략 패턴으로 변하는 것/변하지 않는 것 분리 |
| 3.3 | [UserDaoWithAnonymous](practice/src/main/java/com/example/practice/chapter03/UserDaoWithAnonymous.java) | 익명 내부 클래스 & 람다로 전략 인라인화 |
| 3.4 | [JdbcContext](practice/src/main/java/com/example/practice/chapter03/JdbcContext.java) | 컨텍스트를 별도 클래스로 분리 |
| 3.5 | [UserDaoWithTemplate](practice/src/main/java/com/example/practice/chapter03/UserDaoWithTemplate.java) | `JdbcTemplate` 템플릿/콜백 패턴 |

### 5장. 서비스 추상화

| 항목 | 클래스 | 설명 |
|---|---|---|
| 5.1 | [Level](practice/src/main/java/com/example/practice/chapter05/Level.java) | 레벨 업그레이드 순서 관리 enum |
| 5.1 | [UserDao (ch05)](practice/src/main/java/com/example/practice/chapter05/UserDao.java) | `update()` 추가, Level 매핑 |
| 5.2 | [UserServiceImpl](practice/src/main/java/com/example/practice/chapter05/UserServiceImpl.java) | 순수 비즈니스 로직 `upgradeLevels()` |
| 5.3 | [UserServiceTx](practice/src/main/java/com/example/practice/chapter06/UserServiceTx.java) | 트랜잭션 전담 데코레이터 (SRP) |
| 5.4 | [DummyMailSender](practice/src/main/java/com/example/practice/chapter05/DummyMailSender.java) | 메일 발송 테스트 스텁 |
| 테스트 | [UserServiceTest](practice/src/test/java/com/example/practice/chapter05/UserServiceTest.java) | MockMailSender로 메일 발송 검증 |

### 6장. AOP

| 항목 | 클래스 | 설명 |
|---|---|---|
| 6.1 | [UserServiceTx](practice/src/main/java/com/example/practice/chapter06/UserServiceTx.java) | 트랜잭션 코드 분리 데코레이터 |
| 6.2 | [MockUserDao](practice/src/test/java/com/example/practice/chapter06/MockUserDao.java) | DB 없는 고립 단위 테스트용 목 |
| 6.2 | [UserServiceIsolatedTest](practice/src/test/java/com/example/practice/chapter06/UserServiceIsolatedTest.java) | 스프링 컨텍스트 없는 순수 단위 테스트 |
| 6.3 | [TransactionHandler](practice/src/main/java/com/example/practice/chapter06/TransactionHandler.java) | `InvocationHandler` 다이내믹 프록시 |
| 6.4 | [UserServiceWithTx](practice/src/main/java/com/example/practice/chapter06/UserServiceWithTx.java) | `ProxyFactoryBean` + 어드바이저 설정 |
| 6.5~6.7 | [UserServiceAop](practice/src/main/java/com/example/practice/chapter06/UserServiceAop.java) | `@Transactional` AOP 적용 서비스 |
| 6.8 | [UserServiceAopTest](practice/src/test/java/com/example/practice/chapter06/UserServiceAopTest.java) | `@Transactional` 테스트 자동 롤백 |

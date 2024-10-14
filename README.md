# CBM+ 표준플랫폼

![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white)
![Spring_boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![swagger](https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)  
![workflow](https://github.com/eGovFramework/egovframe-template-simple-backend/actions/workflows/maven.yml/badge.svg)

## 환경 설정

프로젝트에서 사용된 환경 프로그램 정보는 다음과 같다.
| 프로그램 명 | 버전 명 |
| :--------- | :------ |
| java | 17 |
| spring boot | 3.3.1|
| maven | 3.8.4 |

## BackEnd 구동

### CLI 구동 방법

```bash
mvn spring-boot:run
```

### IDE 구동 방법

개발환경에서 프로젝트 우클릭 > Run As > Spring Boot App을 통해 구동한다.

### 구동 후 확인

구동 후, 브라우저에서 `http://localhost:{port}/` 로 확인이 가능하다.  
초기 포트번호는 8080이며 `/src/main/resources/application.properties` 파일의 `server.port` 항목에서 변경 가능하다.  
또한, `http://localhost:{port}/swagger-ui/index.html#/` 로 애플리케이션의 엔드포인트를 확인 가능하다.

### 1. Thymeleaf 연동

#### 1)설정은 application.properties 에서

### 2. 계정 정보

#### 1) ID : admin

#### 2) PW : admin

## Jar 실행시

```bash
java -jar <jar파일명> --spring.profiles.active=<profile명>
```

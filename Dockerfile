# 멀티스테이지 빌드를 위한 Dockerfile

# 1단계: 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼 파일과 설정 파일 복사
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# 실행 권한 부여
RUN chmod +x gradlew

# 의존성 다운로드를 위한 캐시 레이어
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src/ src/

# 애플리케이션 빌드
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jre-jammy

# 애플리케이션 실행을 위한 사용자 생성
RUN groupadd -r spring && useradd -r -g spring spring

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 파일 소유권 변경
RUN chown -R spring:spring /app

# 사용자 변경
USER spring

# 애플리케이션 포트 노출
EXPOSE 8080

# 환경변수 설정
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"
ENV TZ=Asia/Seoul

# 데이터베이스 설정
ENV DB_URL=jdbc:mysql://localhost:3306/polltato
ENV DB_USERNAME=root
ENV DB_PASSWORD=
ENV DB_DDL_AUTO=none

# 메일 설정
ENV MAIL_USERNAME=itcotato@gmail.com
ENV MAIL_PASSWORD=

# 애플리케이션 기본 URL
ENV BASE_URL=http://localhost:8080

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 

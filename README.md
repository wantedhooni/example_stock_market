# 증권사 과제평가

# 환경정보
- JAVA 17

# 구동방법
````
cd kakaopaysec
./gradlew clean && ./gradlew bootRun 
````
# 인프라 주소
- swagger-ui 주소 : http://localhost:8080/swagger-ui/index.html
- H2 db 주소 : http://localhost:8080/h2-console

# API 정보
- REST API는 스웨거로 테스트 케이스를 대신하였습니다.

# 전제 조건
- 기본 데이터는 어플리케이션 구동 이후 생성됩니다.
- - CommandLineRunner의 run 이용해서 생성합니다.
- 거래 데이터는 생성하지 않습니다.
- - 아직 도메인을 잘 모르겠습니다. 
- Stock와 StockDaily는 원칙상으로 연간관계를 맺어야 하지만 연결하지 않았습니다.
- - 데이텨 형 떄문에 불필요한 SELECT까 많이 발생하게 됩니다.
- [SearchStockUseCaseV1.java]은 보편적인 JPA 방식으로 개발하였습니다.
- [SearchStockUseCaseV2.java]은 QueryDsl 기준으로 서비스를 개발하였습니다.


# 고려사항
- 비지니스로직은 usecase 패키지에서 결합한다.
- - 아무리 생각해도 주식 체결등을 생각하면 뒤에 이기종 시스템들이 원격으로 통신해야 할거 같습니다.
- - 도메인과 비지니스 로직이 바로 결합하면 문제가 될거 같다.
- - 의존성 흐름 : controller < usecase < (domain) service < (domain) repository < (domain) Entiry 

# 대용량 트래픽을 견디기 위한 전략
- 장 마감전에 캐싱을 사용할수 있을지는 의문이다
- - 주식키워드 검색시에는 캐싱이 가능할거 같다.
- Replica 설정시 Slave에서 read 할수 있도록 @transactional(readOnly=true)를 붙여 주었다.
- 데이터 랜덤으로 변경시 @Async를 사용해서 비동기로 수행



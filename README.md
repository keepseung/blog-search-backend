# project-sample
다음, 네이버 블로그 검색 어플리케이션 

## 실행하기
1. 블로그 검색
~~~
예시 요청
GET http://localhost:8080/api/blog/search?query=코딩&sort=ACCURACY&page=2&size=13
~~~
- 키워드를 통해 블로그를 검색할 수 있습니다.
- 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원합니다.
- 검색 결과는 Pagination 형태로 제공합니다.
- 검색 소스는 카카오 API의 키워드로 블로그 검색을 이용하고 실패시 네이버 검색 API를 이용하도록 개발했습니다.
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있도록 했습니다.

2. 인기 검색어 목록
~~~
예시 요청
GET http://localhost:8080/api/blog/popular-keyword
~~~
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
- 키워드와 검색 수를 응답 값으로 제공합니다.

## 사용 기술
- Kotlin (JDK 17)
- Spring Boot 2.7.9
- Gradle
- Jpa
- H2 In Memory Database

## 외부 라이브러리 및 오픈소스
- Retrofit : 카카오와 네이버 블로그 외부 API HTTP 통신을 위함
- Coroutine : Retrofit으로 외부 API 호출시 runBlocking으로 쓰레드 Blocking을 위해 사용함
- Mockk, SpringMockk : 테스트시 모킹을 위해 사용함
- Faker : 테스트시 사용할 가짜 데이터 생성을 위함

## 고려했던 사항
* 테스트 케이스 
    * 레이어 별 단위 테스트 및 통합 테스트 개발 진행함
* 에러 처리
    * 예외를 위한 응답, 핸들러, 예외 코드, 메세지 구성
* 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현
    * 낙관적 락을 사용해 검색어 별로 검색된 횟수에 대해 동시성 문제를 예방함
* 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공
 

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
- 검색 소스는 카카오 API의 키워드로 블로그 검색을 이용하고 실패시 네이버 검색 API를 이용하도록 개발했습ㄴ디ㅏ.
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
- Retrofit
- Coroutine
- Mockk, SpringMockk
- faker


 

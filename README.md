# yes48
- 장바구니/주문/상품 관리 기능을 포함한 도서 쇼핑몰입니다.


## Skill


## Architecture
![아키텍쳐](https://github.com/harvee0813/yes48/assets/116448571/3b27be2b-630d-4b32-9831-ec463b47a7be)

## ERD (MySQL)
![MySQL ERD](https://github.com/harvee0813/yes48/assets/116448571/475c9ef3-8ed2-4376-bb6f-815e837ae8e6)

## 기능 설명
|분류|설명|
|:--|:--|
|상품 관리</br>(관리자)|- QueryDSL을 이용한 상품 검색 및 페이징 처리 </br> - 상품 등록/수정 </br> - Amazon S3 이미지 저장 </br> - Bean Validation을 이용한 검증|
|로그인|- Spring Security를 적용한 일반 로그인 & 소셜 로그인(구글/네이버) </br> - 아이디 찾기와 비밀번호 변경|
|회원가입|- kakao 우편번호 서비스 API를 이용한 배송 주소 검색 </br> - ajax를 이용한 ID 중복 확인 </br> - Bean Validation을 이용한 검증|
|마이페이지|- 내정보 확인/수정 </br> - 주문 내역 확인 </br> - 회원 탈퇴|
|상품|- QueryDSL을 이용한 상품 검색 및 페이징 처리 </br> - 상품 상세 페이지에서 수량 변경시 가격 변동|
|장바구니|- 상품 개수 변경 </br> - 상품 삭제 </br> - 총 구매 가격 계산|
|주문|- 주문 완료시 상품 개수 차감|
|오류|- 403, 500 오류페이지 제작|


## 핵심 기능 Sequence diagram
  ![관리자](https://github.com/harvee0813/yes48/assets/116448571/7fe1fc6c-cd5f-4286-a10a-d6a4e0e40610)
  ![회원가입](https://github.com/harvee0813/yes48/assets/116448571/c842afc9-c02b-4426-9a61-2969ce3c07e3)
  ![상품 주문](https://github.com/harvee0813/yes48/assets/116448571/dff9e881-e48b-4c80-9a40-426ce9374655)

## Unit Test
![RepositoryTest-1](https://github.com/harvee0813/yes48/assets/116448571/9104868d-e30c-4e3f-a426-f31a470988cc)
![RepositoryTest-2](https://github.com/harvee0813/yes48/assets/116448571/65d45e88-b0e3-4d2c-87e0-efd7d7b9a484)
![ServiceTest-1](https://github.com/harvee0813/yes48/assets/116448571/32cd3897-6c2f-4668-bfd6-098671be6498)
![ServiceTest-2](https://github.com/harvee0813/yes48/assets/116448571/71d2b1a1-c990-4dea-97c3-8ba5cf9b6dba)

# 제자들교회 여름 수련회 등록 프로그램 
## 개요 
이 소스는 제자들교회에서 장년여름수련회등록 프로그램으로 개발하였습니다.
사용 기술은 Java1.8, gradle, spring-boot-jpa, mysql, thymeleaf, spring-security 등으로 개발하였기 때문에 
위의 기술의 경험 혹은 기반이 있으신 분은 실행하고 수정할 수 있습니다.

제자들교회가 아닌 다른 교회나 단체에서도 실행하여 등록화면을 보시고 제자들 교회와 비슷한 내용을 입력을 하는 경우라면 도입하셔도 됩니다.
이 프로그램은 1년정도 사용하여 검증은 되었으나 도입하기전에 반드시 다양한 케이스를 검증하고 등록 받으셨으면 합니다.


## 사용방법 

### 0.Prerequisites 

- Mysql 5.7 
한글을 처리해야 하므로 문자셋은 UTF-8을 setting 하여 주세요. 설정하지 않으면 한글이 정상 출력되지 않습니다.
- Java 1.8 
- Gradle

### 1.Clone this repo

```
$ git clone https://github.com/peceik/jejadle-retreat.git jejadle-retreat
$ cd jejadle-retreat
```

### 2.Setting Mysql

아래 mysql 계정생성 스크립트의 mysql login password는 retreat 입니다. 다른 계정이나 password 를 사용한다면 application.yml 에서 변경내용을 반영하여 주세요. 


```
CREATE SCHEMA `retreat` ;

create user 'retreat'@'%' identified by 'retreat';
grant all privileges on retreat.* to 'retreat'@'%';
```


### 3.Execute Gradle wrapper 

2018.06 현재 gradle 사용버전은 4.8 입니다. gradle 4.8에서는 제자들교회 수련회 등록 프로그램은 동작하지 않습니다. 
따라서 정상동작하는 2.5 버전으로 동작하도록 설정해야 합니다. 

```
$ cd retreat/retreat-front
$ gradle wrapper
```

### 4.Execute Gradle bootrun

```
$ gradlew bootrun
```

### 5.Check Browser

브라우져를 열어서 http://localhost:8080 으로 이동하여 수련회 페이지를 확인합니다. 


## 화면 구성 및 설정

### 화면
- /public/intro : 수련회에 대한 정보 제공 페이지 입니다. 
- /public/ : 수련회 등록내용을 입력하는 페이지 입니다.
- /admin/list : 수련회 목록 페이지입니다 관리자가 입력내용을 확인하고 수정할 수 있는 화면 입니다.
- /admin/state : 수련회 자료 페이지 입니다. 이 곳에서 수련회 참가자가 입력한 내용을 excel 형태의 자료로 받아볼 수 있습니다. 등록이 모두 끝나면 수련회 진행팀에서 주로 사용하는 화면입니다.

### 설정 

#### /retreat-front/src/main/resources/application.yml  

mysql 및 was 사용 port 등을 설정합니다.
```

#/admin/* 화면에 접근시 사용하는 계정 설정 입니다.
security:
  user.name: admin 
  user.password: 1111 
  user.role: USER 
```

#### /retreat-front/src/main/resources/retreat.properties
```

#/retreat-core/src/main/java/org/jejadle/retreat/core/service/RetreatService.java calculate Method 에서 주로 사용합니다. 
#수련회비 계산 방법의 변경이 필요한 경우 이 메소드를 수정하면 로직이 변경됩니다.

stay.2.date=2018-08-06

meal.1.date=2018-08-05
meal.1.type=dinner
meal.2.date=2018-08-06
meal.2.type=breakfast
meal.3.date=2018-08-06
meal.3.type=lunch
meal.4.date=2018-08-06
meal.4.type=dinner
meal.5.date=2018-08-07
meal.5.type=breakfast
meal.6.date=2018-08-07
meal.6.type=lunch


#SMS 발송시 필요한 계정
#http://www.bluehouselab.com/pages/about-bluehouselab 가입하여 api 키를 받아서 입력합니다.
sms.appid=id
sms.apikey=key
sms.senderNumber=number

#계좌번호 정보 입니다.
retreat.account=\uC6B0\uB9AC\uC740\uD589 1002-955-113841 (\uC608\uAE08\uC8FC: \uBC15\uD61C\uC601)

#부하테스트시 사용합니다. 
retreat.test=Y

#숙박 및 식사 금액을 설정합니다.
stay.price=30000
stay.discount.price=20000
meal.price=5500

```


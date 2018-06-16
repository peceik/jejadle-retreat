# 제자들교회 여름 수련회 등록 Web 프로그램
## 개요 
이 소스는 제자들교회 장년여름수련회 등록을 용도로 하여 개발하였습니다. 
교회의 다수의 가족이 숙박, 식사, 차량 등의 정보를 입력하면 회비를 계산하여 알려주고 입력한 정보는 저장하여 수련회 등록팀에서 열람할 수 있도록 되어 있습니다. 
또한 sms api key를 등록하면 수련회 등록시에 회비와 입금계좌를 문자로 보내도록 되어 있습니다.
Java 기반의 open source를 기반으로 개발 되었습니다. 
구체적으로 JVM 은 Java1.8, 빌드 도구로는 gradle, DB는 mysql 5.7 사용 framework으로는 spring-boot-jpa, velocity, thymeleaf, spring-security 입니다. 
위의 언급한 기술을 모두 모르는 Java 개발자라도 시행착오를 겪어가면서 사용할 수 있습니다.
gradle, spring-boot, jpa 등의 경험이 있는 Java 개발자라면 어렵지 않게 사용할 수 있을 거라 생각합니다.


제자들교회가 아닌 다른 교회나 단체에서도 실행하여 등록화면을 보시고 제자들 교회와 비슷한 내용을 입력을 하는 경우라면 도입하셔도 됩니다.
이 프로그램은 1년정도 사용하여 검증은 되었으나 도입하기전에 반드시 다양한 케이스를 검증하고 다량으로 사용자 등록을 받으시는 것을 추천합니다.


## 사용방법 

### 0.Prerequisites 
- 1 G 이상의 Memory를 가지고 있는 Server
클라우드 서버(AWS, digitalocean)도 상관없습니다. 200 가정의 수련회 등록을 받은 경우에는 AWS 의 freetier 로도 충분히 감당할 수 있었습니다.
- Mysql 5.7 
한글을 처리해야 하므로 문자셋은 UTF-8로 setting 하여 주세요. 설정하지 않으면 한글이 정상 출력되지 않습니다.
- Java 1.8 
- Gradle
- Git


### 1.Clone repo

```
$ git clone https://github.com/peceik/jejadle-retreat.git jejadle-retreat
$ cd jejadle-retreat
```

### 2.Setting Mysql

아래 mysql 계정생성 스크립트 입니다. 이 때 mysql login password는 retreat 입니다. 다른 계정이나 password 를 사용한다면 application.yml 에서 변경내용을 반영하여 주세요. 


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

gradle 이 아닌 gradlew 입니다.
실행 위치는 retreat/retreat-front 입니다.

```
$ gradlew bootrun
```

### 5.Check Browser

브라우져를 열어서 http://localhost:8080 으로 이동하여 수련회 페이지를 확인합니다. 


## 화면 구성 및 설정

### 화면 
아래 화면 url은 프로토톨, 도메인 그리고  port 정보는 생략하였습니다. (ex: http://localhost:8080/public --> /public/)
- /public/intro : 수련회에 대한 정보 제공 페이지 입니다. 
- /public/ : 수련회 등록내용을 입력하는 페이지 입니다.
- /admin/list : 수련회 목록 페이지입니다 관리자가 입력내용을 확인하고 수정할 수 있는 화면 입니다.
- /admin/state : 수련회 자료 페이지 입니다. 이 곳에서 수련회 참가자가 입력한 내용을 excel 형태의 자료로 받아볼 수 있습니다. 등록이 모두 끝나면 수련회 진행팀에서 주로 사용하는 화면입니다.

### 설정 

#### /retreat-front/src/main/resources/application.yml  

mysql 및 was 사용 port 등을 설정합니다.

```
# 기존 설정 생략 
#/admin/* 화면에 접근시 사용하는 계정 설정 입니다.
security:
  user.name: admin 
  user.password: 1111 
  user.role: USER 

# 기존 설정 생략 

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


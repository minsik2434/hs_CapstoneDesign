# 잘먹고 살자

## 개요
프로젝트 명 : 잘 먹고 살자 <br/>
인원 : 4명

## 개발 목적 및 이유
최근 건강을 관리하는 인구가 증가하고 있고 음식마다 영양 정보를 확인하는 사람이 늘어나고 있어 그에 따른 식단 관리를 필요로 한다 다이어트 외에도 알레르기를 가진 사람, 고혈압, 당뇨병, 심혈관계 질환등 지병이 있는 사용자에게도 특정 영양성분에 대한 정보가 필요하며 이에 따른 음식의 상세 정보도 필요로 한다
<br/>
하지만 현재 앱들을 보았을때 식단을 기록하는데 번거로움이 있으며 알레르기나 지병등 개인에 따라 달라지는 요소를 반영하지 못하고 있다 이를 해결하고자 개인 맞춤형 식단 관리 앱을 개발하고자 하였다

## 서비스 흐름도
<img width="313" alt="서비스 흐름도" src="https://github.com/user-attachments/assets/6e40d316-c26a-48a2-8174-99be847a03f1">
<ol>
  <li>
    사용자 정보 설정
    <ol>
      <li>
        닉네임 설정
      </li>
      <li>
        성별, 키, 몸무게, 나이 설정
      </li>
      <li>
        성별, 키, 몸무게, 나이 설정
      </li>
      <li>
        알레르기, 지병 설정
      </li>
      <li>
        활동량 설정
      </li>
      <li>
        권장 칼로리 설정
      </li>
    </ol>
  </li>
  <li>
    메인 화면
    <ol>
      <li>
        당일 섭취 영양소 현황 (탄수화물, 단백질, 지방, 당류, 나트륨, 콜레스테롤, 트랜스지방, 포화지방산)
      </li>
      <li>
        당일 섭취 식단(아침, 점심, 저녁)
      </li>
    </ol>
  </li>
  <li>
    기록하기
    <ol>
      <li>
        이미지 버튼 터치시 3개의 테마(검색하기, 바코드 스캔, 사진인식) 저장 방식이 나옴
      </li>
      <li>
        음식 상세 정보(영양 성분, 원재료) 제공
      </li>
      <li>
        날짜 선택
      </li>
      <li>
        시간 선택(아침,점심,저녁)
      </li>
      <li>
        기록 하기 기능 제공
      </li>
    </ol>
  </li>
  <li>
    달력
    <ol>
      <li>
        권장 칼로리 보다 칼로리를 많이 섭취한 날은 달력에 빨간색으로 표시
      </li>
      <li>
        달력에서 특정 날짜를 선택하면 그 날짜에 어떤 음식을 섭취했는지 정보 제공
      </li>
    </ol>
  </li>
  <li>
    통계
    <ol>
      <li>
        주에 제일 많이 먹은 음식 5가지 정보를 제공
      </li>
      <li>
        그래프를 일, 주, 월별 영양 성분 통계 제공
      </li>
      <li>
        저번주 통계와 비교하여 정보를 제공
      </li>
    </ol>
  </li>
  <li>
    마이페이지
    <ol>
      <li>
        사용자 정보 재설정
      </li>
      <li>
        타임 라인 기능
      </li>
      <li>
        알람 기능
      </li>
    </ol>
  </li>
</ol>

## ERD
![image](https://github.com/user-attachments/assets/1549b185-1b75-480c-a4d2-f085beaba8c7)

## 기능 목표

### 편의성
<ul>
  <li>
    기성품일 경우 바코드 인식을 통해 성분을 확인한다
  </li>
  <li>
    음식 사진을 찍으면 그 음식이 어떤 음식인지 인식하고 평균적인 성분을 반영한다
  </li>
  <li>
    검색하기 버튼을 누르면 직접 음식의 이름을 검색하여 평균적 성분을 반영한다
  </li>
</ul>

### 음식 상세 정보 제공
<ul>
  <li>
    원재료 및 탄수화물, 단백질, 지방, 콜레스테롤, 당류, 염분 등 음식의 상세 정보를 제공한다
  </li>
  <li>
    사용자의 정보에 따라 알레르기 유발 성분이 있는 경우 주의하도록 표시한다
  </li>
</ul>

### 맞춤형 관리 시스템 제공
<ul>
  <li>
    사용자마다의 특징을 반영하여 섭취량을 조절한다
  </li>
  <li>
    통계를 통해 사용자의 섭취 현황을 표시한다
  </li>
</ul>


### 앱 주요 기능

<details>
<summary> 초기 설정 및 권장 칼로리 계산 </summary>

![image](https://github.com/user-attachments/assets/917f990d-619a-4189-b118-14f34f7d2ab7)
사용자가 이름, 키, 몸무게, 활동량, 지병 여부등 정보를 기입하면 "Harris Benedict" 공식을 이용하여 사용자의 영양 성분 섭취 권장량을 표시한다 <br/>
지병이 있는 사용자는 여러 참고 문헌에서 가져온 계산식을 사용하여 권장량을 조절한다. 두 개 이상의 지병이 있는 경우 최솟값을 적용하였다<br/>
(Harris Benedict : 건강한 사람의 연령, 성별, 신장과 체중을 기초로 한 기초 대사량 * 활동 계수 * 부상 계수의 값)
</details>


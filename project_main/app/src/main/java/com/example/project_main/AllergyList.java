package com.example.project_main;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AllergyList {

    private final String[] milkList = {"우유", "유제품", "요구르트", "생크림", "아이스크림", "카라멜", "과자", "빵", "케이크 인공버터향", "버터", "버터우유", "버터밀크", "카제인", "카제인염", "치즈", "커드", "크림", "커스터드", "푸딩",
            "버터크림", "락트알부민", "락토글로불린", "누가", "사워크림", "요거트", "흑설탕향로", "캐러멜 향료", "초콜릿", "마가린", "고단백가루", "천연향료", "우유", "유당",
            "유청", "염소젖", "산양유", "유청", "카제네이트", "락트알부민", "락토글로불린", "락토페리틴", "락토스"};

    private final String[] eggsList = {"달걀","난류","빵류", "제과류", "마요네스", "소스류", "시럽류", "소시지", "국수류", "파스타류", "달걀", "달걀흰자", "달걀노른자", "알부민",
            "알부미네이트", "달걀레시틴", "리베틴", "글로불린", "비텔린", "오보", "오보글로불린", "오보비텔린", "오보알부민", "오보뮤신", "오보뮤코이드", "오보"};

    private final String[] shellfishList = {"갑각류","따개비", "게", "가재", "크레베트 새우", "크릴 새우", "바닷가재", "부채새우붙이", "대하", "새우"};

    private final String[] fishList = {"생선","멸치", "배스", "메기", "대구", "넙치", "그루퍼", "해덕", "헤이크", "넙치", "청어", "만새기", "농어",
            "강꼬치고기", "명태", "연어", "대구 새끼", "서대기", "도미", "황새치", "틸라피아", "송어", "참치"};

    private final String[] nutsList = {"견과류","땅콩", "브라질 너트", "아몬드", "캐슈넛", "피스타치오", "피칸 너트", "헤이즐넛", "호두"};

    private final String[] beanList = {"대두","콩","증량제", "캐롭", "유화제", "구아검", "아라비아검", "가수분해 식물성 단백질", "HVP", "레시틴", "된장", "글루탐산소다", "MSG", "단백질", "단백질 증량제", "콩가루", "콩 견과류",
            "콩 판테놀", "콩 단백질", "콩 분리 단백질", "콩 분리 농축제", "간장", "대두유", "안정제", "전분", "질감이 있는 식물 단백질", "TVP", "증점제", "야채 육수", "야채 껌", "야채 전분", "식물성 전분"};

    private final String[] wheatList = {"밀","소불고기양념", "호밀식빵", "글루텐", "보리", "귀리", "호밀", "밀가루", "밀", "소맥분", "전분", "소맥전분"};

    private final String[] peanutList = {"땅콩","아라키산 오일", "아라키스", "아라키스 땅콩", "인공 견과류", "맥주 견과류", "삶은 땅콩", "냉압착 땅콩 기름", "으깬 견과류", "earth nuts", "goober peas",
            "ground nuts", "가수분해 땅콩 단백질", "마델나스", "혼합 견과류", "껍질을 까지 않은 땅콩", "땅콩 맛 견과류", "견과류 조각", "견과육", "땅콩 버터", "땅콩 버터 칩",
            "땅콩 버터 소량", "땅콩 밀가루", "땅콩 페이스트", "땅콩 소스", "땅콩 시럽", "스페인 땅콩", "버지니아 땅콩"};

    private final String[] meatList = {"돼지고기","쇠고기","닭고기","닭정육","오리고기","양고기","염소","유산양","식육","포장육","식육가공품"};



    private final ArrayList<String> milkArrayList = new ArrayList<>(Arrays.asList(milkList));
    private final ArrayList<String> eggsArrayList = new ArrayList<>(Arrays.asList(eggsList));
    private final ArrayList<String> shellfishArrayList = new ArrayList<>(Arrays.asList(shellfishList));
    private final ArrayList<String> fishArrayList = new ArrayList<>(Arrays.asList(fishList));
    private final ArrayList<String> nutsArrayList = new ArrayList<>(Arrays.asList(nutsList));
    private final ArrayList<String> beanArrayList = new ArrayList<>(Arrays.asList(beanList));
    private final ArrayList<String> wheatArrayList = new ArrayList<>(Arrays.asList(wheatList));
    private final ArrayList<String> peanutArrayList = new ArrayList<>(Arrays.asList(peanutList));
    private final ArrayList<String> meatArrayList = new ArrayList<>(Arrays.asList(meatList));

    public ArrayList<String> getAllergyArrayList(int allergyNum){

        ArrayList<String> allergy = new ArrayList<>();

        switch (allergyNum) {
            case 0:
                allergy = milkArrayList;
                break;
            case 1:
                allergy = eggsArrayList;
                break;
            case 2:
                allergy = shellfishArrayList;
                break;
            case 3:
                allergy = fishArrayList;
                break;
            case 4:
                allergy = nutsArrayList;
                break;
            case 5:
                allergy = beanArrayList;
                break;
            case 6:
                allergy = wheatArrayList;
                break;
            case 7:
                allergy = peanutArrayList;
                break;
            case 8:
                allergy = meatArrayList;
                break;

        }
        return allergy;
    }

}

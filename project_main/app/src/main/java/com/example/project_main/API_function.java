package com.example.project_main;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class API_function {


    String Barcodekey = "78d4923e65234bd4994c";
    String HACCPKEY = "Xd2Yy7OLRNMYdBz3BThVsTzQnoBlWeeB4ClMz52gNBB%2FNDXpxuRpEzHUDdRE8X%2By48oE65X%2FjDB9zpzmCt6qiw%3D%3D";

    String string_data;
    TextView text;

    String dataSearchByBarcode(String barcodeNo) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.foodsafetykorea.go.kr/api");
        urlBuilder.append("/" + Barcodekey); /* Service Key */
        urlBuilder.append("/" + "C005"); //api 종류에 따른 코드 *바코드연계제품api*
        urlBuilder.append("/" + "json"); //json 타입
        urlBuilder.append("/" + "1"); // 요청시작위치
        urlBuilder.append("/" + "2"); // 요청종료위치
        urlBuilder.append("/" + "BAR_CD=" + barcodeNo); //json 타입
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();

    }

    public String[] itemsByBarcodeJsonParser(String jsonString) {

        String prdlstNo = null; //픔먹 보고번호
        String prdlstNm = null; //제품명
        String[] arr = new String[2];

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject C005 = jsonObject.getJSONObject("C005"); // body키의 값들
            System.out.println("1번째 : "+C005);
            JSONArray items = C005.getJSONArray("row"); // body키의 값들(items,totalCount...등) 중 items키의 값들 JSONArray를 가져옴.
            System.out.println("2번째 :" +items);

            HashMap map = new HashMap<>();
            JSONObject jObject = items.getJSONObject(0);
            System.out.println("3번째 :" +jObject);
            prdlstNm = jObject.optString("PRDLST_NM");
            prdlstNo = jObject.optString("PRDLST_REPORT_NO");
            arr[0] = prdlstNm;
            arr[1] = prdlstNo;


        } catch (JSONException e) {
            arr[0] = null;
            arr[1] = null;
            return arr;
        }
        return arr;
    }

    String dataSearchByPrdNo(String PrdNo) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553748/CertImgListService/getCertImgListService"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+HACCPKEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("prdlstReportNo","UTF-8") + "=" + URLEncoder.encode(PrdNo, "UTF-8")); /*제품에 부여되는 고유식별번호*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과 응답 형식*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();

    }

    public String[] itemsByPrdNoJsonParser(String jsonString) {


        String rawmtrl = null; //원재료
        String imgurl1 = null; //제품이미지url
        String allergy = null; //알레르기 유발물질

        String[] arraysum = new String[3];
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject body = jsonObject.getJSONObject("body"); // body키의 값들
            JSONArray items = body.getJSONArray("items"); // body키의 값들(items,totalCount...등) 중 items키의 값들 JSONArray를 가져옴.


            HashMap map = new HashMap<>();
            JSONObject jObject = items.getJSONObject(0);
            JSONObject item = jObject.getJSONObject("item");

            rawmtrl = item.optString("rawmtrl");
            allergy = item.optString("allergy");
            imgurl1 = item.optString("imgurl1");

            arraysum[0] = rawmtrl;
            arraysum[1] = imgurl1;
            arraysum[2] = allergy;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }



    String AllergySearchByName(String prdnm) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.foodsafetykorea.go.kr/api");
        urlBuilder.append("/" + Barcodekey); /* Service Key */
        urlBuilder.append("/" + "C002"); //api 종류에 따른 코드 *바코드연계제품api*
        urlBuilder.append("/" + "json"); //json 타입
        urlBuilder.append("/" + "1"); // 요청시작위치
        urlBuilder.append("/" + "2"); // 요청종료위치
        //urlBuilder.append("/" + "PRDLST_NM=" + prdnm); //json 타입
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    public String[] allergyJsonParser(String jsonString) {

        String prdlstNo = null; //픔먹 보고번호
        String prdlstNm = null; //제품명
        String[] arr = new String[2];

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject C002 = jsonObject.getJSONObject("C002"); // body키의 값들
            System.out.println("1번째 : "+C002);
            JSONArray items = C002.getJSONArray("row"); // body키의 값들(items,totalCount...등) 중 items키의 값들 JSONArray를 가져옴.
            System.out.println("2번째 :" +items);

            HashMap map = new HashMap<>();
            JSONObject jObject = items.getJSONObject(0);
            System.out.println("3번째 :" +jObject);
            prdlstNm = jObject.optString("PRDLST_NM");
            prdlstNo = jObject.optString("PRDLST_REPORT_NO");
            arr[0] = prdlstNm;
            arr[1] = prdlstNo;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }

    // 음식 이름으로 해썹 api 검색해 데이터 가져오기
    String dataSearchByPrdNm(String PrdNm) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553748/CertImgListService/getCertImgListService"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+HACCPKEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("prdlstNm","UTF-8") + "=" + URLEncoder.encode(PrdNm, "UTF-8")); /*제품에 부여되는 고유식별번호*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과 응답 형식*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    public String[] itemsByPrdNmParser(String jsonString,String pnm) {  //음식 이름 입력해서 그 음식이름과 일치하는 음식 포함성분 가져오기
        String rawmtrl = null; //원재료
        String prdNm = null;
        String imgurl = null;
        String[] arraysum = new String[3];
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject body = jsonObject.getJSONObject("body"); // body키의 값들
            JSONArray items = body.getJSONArray("items"); // body키의 값들(items,totalCount...등) 중 items키의 값들 JSONArray를 가져옴.

            JSONObject json = null;
            for(int i=0; i<items.length(); i++){
                json = items.getJSONObject(i);
                if(json != null){
                    JSONObject item = json.getJSONObject("item");
                    prdNm = item.optString("prdlstNm");
                    if(prdNm.equals(pnm)==true) {
                        System.out.println(prdNm);
                        rawmtrl = item.optString("rawmtrl");
                        imgurl = item.optString("imgurl1");
                        break;
                    }
                }
            }
            arraysum[0] = rawmtrl;
            arraysum[1] = imgurl;

        } catch (JSONException e) {
            e.printStackTrace();
            arraysum[0] = "error";
            arraysum[1] = "error";
        }
        return arraysum;
    }

}


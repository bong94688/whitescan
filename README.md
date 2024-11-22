## a. 인구 등록 
 ### 엔드포인트: POST /api/regions
• 기능: 지역별 인구 데이터 입력
• 요청 본문: 지역 이름, 인구수, 날짜, 시간
• 응답: 저장된 데이터 정보

## b. 인구 통계 조회 API
 ### 엔드포인트: GET /api/regions/stats
• 기능: 특정 지역, 특정 날짜의 인구 통계 조회
• 요청 파라미터: 지역 이름, 날짜
• 응답:
{
"현재인구수": 숫자,
"1 시간전대비증감률": 숫자,
"3 시간전대비증감률": 숫자
}

## c 월별 통계 API

### 엔드포인트: GET /api/regions/monthly-stats
• 기능: 특정 지역의 월별 인구 통계 조회
• 요청 파라미터: 지역 이름, 연도, 월
• 응답:
{
"월평균인구수": 숫자,
"전월대비증감률": 숫자,
}

```
- velog https://velog.io/@bong9468/posts
```

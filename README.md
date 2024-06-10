## 1. Fragment + ViewPager + Indicator 만들기
![2024-06-05 15;02;53](https://github.com/chihyeonwon/how_weather/assets/58906858/986be614-1e8a-4e3d-84ec-7406dea64d7b)

## 2. **오늘 날씨 화면 UI 만들기**    
![image](https://github.com/chihyeonwon/how_weather/assets/58906858/176fe121-30e9-4658-b706-471a655d4be0)

    - 화면 사진과 속성을 참고하여 직접 UI를 구성해 보거나 코드스니펫을 활용해 주세요.
    
    <aside>
    💡 위치나 색상 등의 속성은 모두 변경 가능하나 **ID는 꼭 유지해 주세요**. ID도 변경하고 싶으실 경우 코드에서 사용되는 이름도 함께 변경되어야 합니다.)
    
    </aside>

- **Top 영역**
    - 지역 선택 : TextView (#**9DA7AE, 12sp)**
    - 서울특별시 : TextView (#**323537, 20sp)**
    - 화살표 : ImageView (drawable.**ic_arrow_right)**
- **Blue 영역**
    - 날씨 상태별 아이콘 : ImageView (133dp*92dp)
        - ic_color_sunny/cloudy/sunny_cloudy
    - 맑음 : TextView (#**FFFFFF, 20sp, Bold)**
    - 온도 : TextView(#**FFFFFF, 110sp, Bold)**
    - background : ConstraintLayout(높이 :281dp)
        - drawable.main_weather_background.xml
- **강수량 영역**
    - 강수량 상태별 아이콘 : ImageView (133dp*92dp)
        - ic_sunny/cloudy/sunny_cloudy/water/snow/rainy
    - background : ConstraintLayout(높이 :130dp)
        - drawable.**rectangle_background**.xml
        - **elevation: 10dp**
        - marginHorizontal: 30dp
- **풍속/습도 영역:** 강수량 영역 참고

- 
![image](https://github.com/chihyeonwon/how_weather/assets/58906858/7e50321f-eb99-4b46-87ed-f90ff6e7a6fe)
```
시간대 별 날씨, 기온, 강수 형태를 보여주는 리스트를 보여주는 날짜 리스트 페이지를 구성하였다.
```
## 오늘 날씨 화면 & 시간대 별 날씨 리스트 화면 구현
![2024-06-10 19;39;39](https://github.com/chihyeonwon/how_weather/assets/58906858/a691393a-c957-496c-b41a-5ea976cc15f0)

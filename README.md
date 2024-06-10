## What is How Weather Applicatoin?
How초단기실황, 초단기예보, 단기((구)동네)예보, 예보버전 정보를 조회하는 앱 서비스입니다.        
초단기실황정보는 예보 구역에 대한 대표 AWS 관측값을, 초단기예보는 예보시점부터 6시간까지의 예보를,     
단기예보는 예보기간을 글피까지 확장 및 예보단위를 상세화(3시간→1시간)하여 시공간적으로 세분화한 예보를 제공합니다.    
     
Develoepr. Won Chi Hyeon, MIT License
- 

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

## 공공 데이터 포탈에서 오늘 날씨 정보 찾기!
정부 및 공공 기관이 보유한 다양한 데이터를 일반인, 개발자, 기업 등에게 공개하는 곳으로, 
날씨, 교통, 보건, 교육 등 다양한 분야의 데이터를 제공하고 있답니다!

#### 공공데이터 포털 데이터 신청
![image](https://github.com/chihyeonwon/How_Weather/assets/58906858/03d9c8b5-d8be-40e9-8d1b-e187b457ef62)

#### 요청 변수에 대한 도메인 이해 (단기 예보 3일, 5시 발표)
![image](https://github.com/chihyeonwon/How_Weather/assets/58906858/7cceaf6a-27d1-425c-97fe-355480488637)

## **02.** Open API 연결과 예외 처리: 안정성 확보

<aside>
✔️ 기상청 API 문서를 참고하여 Retrofit을 이용해 서버에 연결하고, 예외 상황에 대한 처리를 추가해 보아요.
</aside>

1. **라이브러리 추가: 먼저, 프로젝트의 build.gradle 파일에 Retrofit 라이브러리와 데이터 변환을 위한 Gson 컨버터 라이브러리를 추가하세요.** 
안정적인 API 연결 및 디버깅을 위해 okhttp 로깅 라이브러리도 함께 추가해 주세요.   
```kotlin
dependencies {
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // For Debugging
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
```
2. **Retrofit 인스턴스 생성**
    1. Retrofit Builder를 사용하여 Retrofit Instance를 생성하고, BaseUrl과 Data Converter를 설정
    2. 로또 때와 거의 유사합니다. 다만 BaseUrl만 변경해 주고, 안정성을 위해 **HttpLoggingInterceptor**를 설정 추가해 주세요.
```kotlin
object RetrofitInstance {
    private const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: WeatherService by lazy { retrofit.create(WeatherService::class.java) }
}
```
3. **API 인터페이스 정의: 서비스의 각 HTTP 엔드 포인트에 대해 메서드를 정의하는 인터페이스를 생성**
![image](https://github.com/chihyeonwon/How_Weather/assets/58906858/c524bc5f-698a-4a2a-923b-e756c1aac476)
#### 단기예보 현업운영 발표시간 별 예보시각 
![image](https://github.com/chihyeonwon/How_Weather/assets/58906858/16e52f60-273d-4e20-8123-48d067a276d5)
#### nx, ny 지역별 x, y 위치값 
![image](https://github.com/chihyeonwon/How_Weather/assets/58906858/e6064750-c7da-4e47-8c23-7c59dcdaa205)
```kotlin
import retrofit2.http.GET
import retrofit2.http.Query

// 배포시에는 안전하게 보완 적용 필요
// 공공 데이터 포탈에서 발급 받은 자신만의 API키를 입력해 주세요.
private const val API_KEY =
    "v16R7......................SV6rsflkslnlEmqkbsYI%2BQ%3D%3D"

interface WeatherService {

    @GET("getVilageFcst?serviceKey=$API_KEY")
    suspend fun getWeather(
        @Query("base_date") baseDate: Int,
        @Query("base_time") baseTime: String = "0500",
        @Query("nx") nx: String = "60",
        @Query("ny") ny: String = "121",
        @Query("numOfRows") numOfRows: Int = 12,
        @Query("pageNo") pageNo: Int = 1,
        @Query("dataType") dataType: String = "JSON"
    ): WeatherModel
}
```

4. 

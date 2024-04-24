package seminar1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seminar1.weather.DailyForecast;
import seminar1.weather.Weather;

import java.util.List;

import static io.restassured.RestAssured.given;

public class FiveDaysofDailyForecastsTest extends AccuweatherAbstractTest{
    @Test
    void testFiveDays() {

        Weather weather = given().queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/5day/294021")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().as(Weather.class);

        Assertions.assertEquals(5, weather.getDailyForecasts().size());;
        Assertions.assertEquals("F", weather.getDailyForecasts().get(0).getTemperature().getMaximum().getUnit());
        Assertions.assertNotNull(weather.getHeadline());
    }


    @Test
    void FiveDaysContainsParams() {
        String result = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/5day/290396")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().asString();

        Assertions.assertTrue(result.contains("DailyForecasts"));
        Assertions.assertTrue(result.contains("Headline"));;
    }
}

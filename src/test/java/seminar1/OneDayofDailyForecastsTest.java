package seminar1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seminar1.weather.DailyForecast;
import seminar1.weather.Weather;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OneDayofDailyForecastsTest extends AccuweatherAbstractTest{

    @Test
    void testOneDay() {

        String result = given().queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/294021")
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
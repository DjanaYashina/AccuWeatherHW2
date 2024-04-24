package seminar1.sem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import seminar1.AccuweatherAbstractTest;

import static io.restassured.RestAssured.given;

public class TenDaysTest extends AccuweatherAbstractTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 333, 15})
    void getTenDays_shouldReturn401(int code){
        given()
                .queryParam("apikey", getApiKey())
                .pathParams("version", "v1")
                .pathParams("location", code)
                .when()
                .get(getBaseUrl() + "/forecasts/{version}/daily/10day/{location}")
                .then()
                .statusCode(401);
    }
}
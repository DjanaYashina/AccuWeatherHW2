package seminar1;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AllDayHistoricalCurrentConditionsTest extends AccuweatherAbstractTest {
    @Test
    void testDayHistoricalCurrent() {
        String response = given()
                .when()
                .get(getBaseUrl() + "/currentconditions/v1/294021/historical/24")
                .then()
                .statusCode(200) // Проверка кода ответа 200
                .extract()
                .response()
                .asString();

        JsonPath jsonPath = new JsonPath(response);

        Assertions.assertEquals(24, jsonPath.getList("").size());
        Assertions.assertTrue(response.contains("LocalObservationDateTime"));
        Assertions.assertTrue(response.contains("Link"));
    }
}

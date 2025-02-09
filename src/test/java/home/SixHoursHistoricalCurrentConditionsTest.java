package home;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SixHoursHistoricalCurrentConditionsTest extends AccuweatherAbstractTest {
    @Test
    void testResponseData() {
        String response = given()
                .when()
                .get(getBaseUrl() + "/currentconditions/v1/294021/historical")
                .then()
                .statusCode(200) // Проверка кода ответа 200
                .extract()
                .response()
                .asString();

        JsonPath jsonPath = new JsonPath(response);

        Assertions.assertEquals(6, jsonPath.getList("").size());
    }
}

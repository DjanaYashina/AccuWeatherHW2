package home;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GovernmentIssuedStormsByYearTest extends AccuweatherAbstractTest{
    @Test
    void testStormErrorResponses() {
        String response = given()
                .when()
                .get(getBaseUrl() + "/tropical/v1/gov/storms/2023")
                .then()
                .statusCode(401) // Проверка кода ответа 401
                .extract()
                .response()
                .asString();

        JsonPath jsonPath = new JsonPath(response);

        Assertions.assertEquals("Unauthorized", jsonPath.getString("Code"));
        Assertions.assertEquals("Api Authorization failed", jsonPath.getString("Message"));
    }
}

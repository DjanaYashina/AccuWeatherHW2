package home;

import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GeopositionSearchTest extends AccuweatherAbstractTest {
    @Test
    void FindMoscowOnGeoposition() {
        String result = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "55.79, 37.53")
                .when()
                .get(getBaseUrl() + "/locations/v1/cities/geoposition/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .asString();
        JsonPath jsonPath = new JsonPath(result);

        Assertions.assertEquals(jsonPath.getString("City.LocalizedName"), "Moscow");
        Assertions.assertEquals(jsonPath.getString("Country.ID"), "RU");
    }
}

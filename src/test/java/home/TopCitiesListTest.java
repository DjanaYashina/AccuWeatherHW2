package home;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import home.LocationsAPI.TopCities.TopCities;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.BDDMockito.given;

public class TopCitiesListTest extends AccuweatherAbstractTest{
    @Test
    void getCities(){

        List<TopCities> result = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/locations/v1/topcities/50")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", TopCities.class);

        Assertions.assertEquals(50, result.size());;
    }
}

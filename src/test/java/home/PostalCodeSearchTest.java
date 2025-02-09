package home;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import home.LocationsAPI.TopCities.PostalCodeSearch;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostalCodeSearchTest extends AccuweatherAbstractTest{
    @Test
    void FindCitiesForPostalCode() {
        List<PostalCodeSearch> result = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "101000")
                .when()
                .get(getBaseUrl() + "/locations/v1/postalcodes/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".",PostalCodeSearch.class);
        boolean cityCountryFound = false;
        String expectedCityName = "Moscow";
        String expectedCountryID = "RU";
        String postalCodeToSearch = "101000";

        for (PostalCodeSearch cityData : result) {
            if (cityData.getPrimaryPostalCode().equals(postalCodeToSearch)) {
                Assertions.assertEquals(expectedCityName, cityData.getAdministrativeArea().getEnglishName(), "City name mismatch");
                Assertions.assertEquals(expectedCountryID, cityData.getCountry().getId(), "Country ID mismatch");
                cityCountryFound = true;
                break;
            }
        }
    }
}

package home;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import home.LocationsAPI.Country;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.BDDMockito.given;

public class CountryListTest extends AccuweatherAbstractTest{

    @Test
    void checkCountryInRegion(){

        List<Country> result = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/locations/v1/countries/EUR")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Country.class);

        Assertions.assertEquals(52, result.size());
        Country russia = result.stream()
                .filter(countrysem -> "RU".equals(countrysem.getId()))
                .findAny().orElse(null);
        Assertions.assertNotNull(russia);
        Assertions.assertEquals("Russia", russia.getLocalizedName());
        Assertions.assertEquals("Russia", russia.getEnglishName());
    }
    @ParameterizedTest
    @ValueSource(strings = { "Austria", "Belgium", "France", "Latvia", "Serbia" })
    void testCheckSpecificCountries(String countryName) {
        List<Country> result = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/locations/v1/countries/EUR")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Country.class);

        boolean countryExists = result.stream()
                .anyMatch(countrysem -> countryName.equals(countrysem.getLocalizedName()));

        Assertions.assertTrue(countryExists, countryName + " not found in the response.");
    }
}

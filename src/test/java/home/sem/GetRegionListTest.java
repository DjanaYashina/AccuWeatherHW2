package home.sem;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import home.AccuweatherAbstractTest;
import home.LocationsAPI.TopCities.Region;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class GetRegionListTest extends AccuweatherAbstractTest {

    private static WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        wireMockServer.stubFor(get(urlPathEqualTo("/locations/v1/regions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("regionListResponse.json")));
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getRegions() {
        List<Region> result = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get("http://localhost:" + wireMockServer.port() + "/locations/v1/regions")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList(".", Region.class);

        assertEquals(10, result.size());
    }
}

package in.reqres.tests;

import in.reqres.models.lombok.ListUserInfoResponseLombokModel;
import in.reqres.models.pojo.ResourceListPojoModel;
import in.reqres.models.pojo.UserDataPojoModel;
import in.reqres.specs.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRequestTests {
    /**
     * GET uri
     * /api/users?page=2
     * /api/users/2
     * /api/users/23
     * /api/unknown
     * /api/unknown/2
     * /api/unknown/23
     * /api/users?delay=3
     */
    @Test
    void checkListUsersWithPojoAndSpecsTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());

        List<UserDataPojoModel> users = given()
                .log().method()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users?page=2")
                .then()
                .extract().body().jsonPath().getList("data", UserDataPojoModel.class);

        List<String> avatars = users.stream().map(UserDataPojoModel::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    void checkSingleUserInfoTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());

        int expectedId = 2;
        String expectedEmail = "janet.weaver@reqres.in";

        Response response = given()
                .when()
                .get("/api/users/2")
                .then()
                .body("data.id", notNullValue())
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        int responseId = jsonPath.get("data.id");
        String responseEmail = jsonPath.get("data.email");

        Assertions.assertEquals(expectedId, responseId);
        Assertions.assertEquals(expectedEmail, responseEmail);
        Assertions.assertEquals(response.jsonPath().getString("data.first_name"), "Janet");
    }

    @Test
    void checkSingleUserWithPojoAndSpecsTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecError404());
        given()
                .log().method()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users/23");
    }

    @Test
    void checkSortedYearTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());

        List<ResourceListPojoModel> resourceList = given()
                .when()
                .get("/api/unknown")
                .then()
                .extract().body().jsonPath().getList("data", ResourceListPojoModel.class);
        List<Integer> years = resourceList.stream().map(ResourceListPojoModel::getYear).collect(Collectors.toList());
        List<Integer> sortedYear = years.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(sortedYear, years);
//        System.out.println(years);
//        System.out.println(sortedYear);
    }

    @Test
    void checkSingleResourceTest() {

        given()
                .spec(Specification.requestSpec())
                .when()
                .get("/api/unknown/2")
                .then()
                .spec(Specification.responseSpecUnique(200))
                .statusLine("HTTP/1.1 200 OK")
                .header("Content-Type", "application/json; charset=utf-8")
                .headers("Transfer-Encoding", "chunked", "X-Powered-By", "Express")
                .contentType("application/json; charset=utf-8")
                .body("data.name", equalTo("fuchsia rose"))
                .body("data.color", equalTo("#C74375"));
    }

    @Test
    void checkSingleResourceNotFoundTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecError404());

        given()
                .spec(Specification.requestSpec())
                .when()
                .get("/api/unknown/{id}", 23);
    }

    @Test
    void checkDelayResponseTest() {

        List<ListUserInfoResponseLombokModel> responseUserInfo =
                given()
                        .spec(Specification.requestSpec())
                        .when()
                        .get("/api/users?delay=3")
                        .then()
                        .spec(Specification.responseSpecOK200())
                        .time(lessThan(5000L))
                        .extract()
                        .body().jsonPath().getList("data", ListUserInfoResponseLombokModel.class);

        responseUserInfo.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        responseUserInfo.forEach(x -> Assertions.assertTrue(x.getEmail().contains(x.getFirst_name().toLowerCase())));

        Assertions.assertTrue(responseUserInfo.stream().allMatch(n -> n.getAvatar().endsWith(".jpg")));
    }
}

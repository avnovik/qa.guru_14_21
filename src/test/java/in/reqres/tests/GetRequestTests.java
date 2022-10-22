package in.reqres.tests;

import in.reqres.models.pojo.ResourceListPojoModel;
import in.reqres.models.pojo.UserDataPojoModel;
import in.reqres.specs.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        users.forEach(x -> Assertions.assertTrue(x.getEmail().contains(x.getFirst_name().toLowerCase())));

        Assertions.assertTrue(users.stream().allMatch(n -> n.getAvatar().endsWith(".jpg")));

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
                .body("data.id", is(2))
                .body("data.email", notNullValue())
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        int responseId = jsonPath.get("data.id");
        String responseEmail = jsonPath.get("data.email");

        Assertions.assertEquals(expectedId, responseId);
        Assertions.assertEquals(expectedEmail, responseEmail);
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
                .baseUri("")
                .when()
                .get("/api/unknown/2")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
    }

    @Test
    void checkSingleResourceNotFoundTest() {

        given()
                .baseUri("")
                .when()
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .extract()
                .jsonPath();
    }


    @Test
    void checkDelayResponseTest() {

        given()
                .baseUri("")
                .when()
                .get("/api/users?delay=3")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
    }

    @Test
    void test() {
        ResponseSpecification responseSpec = expect()
                .statusCode(200);

        given()
                .expect()
                .spec(responseSpec)
                .when()
                .get("/hello");

        given()
                .expect()
                .spec(responseSpec)
                .when()
                .get("/goodbye");
    }

}

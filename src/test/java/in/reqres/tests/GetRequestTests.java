package in.reqres.tests;

import in.reqres.models.pojo.UserDataPojoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class GetRequestTests {

    @Test
    void listUsersTest() {
        List<UserDataPojoModel> users = given()
                .when()
                .contentType(JSON)
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .extract().body().jsonPath().getList("data", UserDataPojoModel.class);

        Assertions.assertTrue(users.stream().allMatch(n -> n.getAvatar().endsWith(".jpg")));

    }
}

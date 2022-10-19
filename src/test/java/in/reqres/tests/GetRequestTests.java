package in.reqres.tests;

import in.reqres.models.pojo.UserDataPojoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class GetRequestTests {

    @Test
    void listUsersTest() {
        List<UserDataPojoModel> users = given()
                .log().uri()
                .when()
                .contentType(JSON)
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .extract().body().jsonPath().getList("data", UserDataPojoModel.class);
        //users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        //users.forEach(x -> Assertions.assertTrue(x.getEmail().contains(x.getFirst_name().toLowerCase(Locale.ROOT))));

        //Assertions.assertTrue(users.stream().allMatch(n -> n.getAvatar().endsWith(".jpg")));

        List<String> avatars = users.stream().map(UserDataPojoModel::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }


    }
}

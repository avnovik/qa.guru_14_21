package in.reqres.tests;

import in.reqres.models.pojo.ResponseUserUpdatePojoModel;
import in.reqres.models.pojo.UserUpdatePojoModel;
import in.reqres.specs.Specification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static io.restassured.RestAssured.given;

public class PutRequestTests {

    @Test
    void checkTimeUpdateUserTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());
        //{ "name": "morpheus", "job": "zion resident" }
        UserUpdatePojoModel updateUser = new UserUpdatePojoModel("morpheus", "zion resident");

        ResponseUserUpdatePojoModel responseUserUpdate = given()
                .body(updateUser)
                .when()
                .put("/api/users/2")
                .then()
                .extract().as(ResponseUserUpdatePojoModel.class);

        String regex = "\\..*$";              // regex101.com  "2022-10-21T11:14:22.902Z"
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        Assertions.assertEquals(currentTime, responseUserUpdate.getUpdatedAt().replaceAll(regex, ""));

//        System.out.println(Clock.systemUTC().instant().toString() + " SystemTime without regex");
//        System.out.println(currentTime + "         SystemTime with regex");
//
//        System.out.println(responseUserUpdate.getUpdatedAt() + "    ResponseTime without regex");
//        System.out.println(responseUserUpdate.getUpdatedAt().replaceAll(regex, "") + "         ResponseTime with regex");

    }
}

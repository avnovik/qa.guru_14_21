package in.reqres.tests;

import in.reqres.models.pojo.UserRegistrationPojoModel;
import in.reqres.models.pojo.UserSuccessfulRegistrationPojoModel;
import in.reqres.models.pojo.UserUnsuccessfulRegistrationPojoModel;
import in.reqres.specs.Specification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostRequestTests {

    @Test
    void checkSuccessfulRegistrationTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());
        //{ "id": 4, "token": "QpwL5tke4Pnpja7X4" }
        int id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        UserRegistrationPojoModel user = new UserRegistrationPojoModel("eve.holt@reqres.in", "pistol");

        UserSuccessfulRegistrationPojoModel successfulRegistration = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UserSuccessfulRegistrationPojoModel.class);

        Assertions.assertNotNull(successfulRegistration.getId());
        Assertions.assertNotNull(successfulRegistration.getToken());

        Assertions.assertEquals(id, successfulRegistration.getId());
        Assertions.assertEquals(token, successfulRegistration.getToken());
    }

    @Test
    void checkUnsuccessfulRegistrationWithoutPasswordTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecError400());
        String expectedErrorText = "Missing password";     //{ "error": "Missing password" }

        UserRegistrationPojoModel user = new UserRegistrationPojoModel("sydney@fife", "");
        UserUnsuccessfulRegistrationPojoModel unsuccessfulRegistration = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UserUnsuccessfulRegistrationPojoModel.class);

        Assertions.assertEquals(expectedErrorText, unsuccessfulRegistration.getError());
    }
}

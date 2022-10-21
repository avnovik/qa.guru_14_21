package in.reqres.tests;

import in.reqres.specs.Specification;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteRequestTests {

    @Test
    void deleteUserTest(){
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecUnique(204));
            given()
                    .filter(new AllureRestAssured())
                    .when()
                    .delete("/api/users/2")
                    .then();
    }
}

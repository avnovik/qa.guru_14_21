package in.reqres.tests;

import in.reqres.specs.Specification;
import org.junit.jupiter.api.Test;

import static in.reqres.helper.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;

public class DeleteRequestTests {

    @Test
    void deleteUserTest() {
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecUnique(204));
        given()
                //.filter(new AllureRestAssured())
                .filter(withCustomTemplates())
                .when()
                .delete("/api/users/2")
                .then();
    }
}

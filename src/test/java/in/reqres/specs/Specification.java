package in.reqres.specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class Specification {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpecError400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpecError404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpecUnique(int responseStatus) {
        return new ResponseSpecBuilder()
                .expectStatusCode(responseStatus)
                .log(ALL)
                .build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}

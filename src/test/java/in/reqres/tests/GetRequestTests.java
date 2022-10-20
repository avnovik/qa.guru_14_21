package in.reqres.tests;

import in.reqres.models.pojo.ResourceListPojoModel;
import in.reqres.models.pojo.UserDataPojoModel;
import in.reqres.specs.Specification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class GetRequestTests {

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
    void checkSortedYearTest(){
        Specification.installSpecification(Specification.requestSpec(), Specification.responseSpecOK200());

        List<ResourceListPojoModel> resourceList = given()
                .when()
                .get("/api/unknown")
                .then()
                .extract().body().jsonPath().getList("data", ResourceListPojoModel.class);
        List<Integer> years = resourceList.stream().map(ResourceListPojoModel::getYear).collect(Collectors.toList());
        List<Integer> sortedYear = years.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(sortedYear,years);
        System.out.println(years);
        System.out.println(sortedYear);
    }
}

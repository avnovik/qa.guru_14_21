package in.reqres.models.pojo;

public class UserSuccessfulRegistrationPojoModel {
    //{ "id": 4, "token": "QpwL5tke4Pnpja7X4" }
    private Integer id;
    private String token;

    public UserSuccessfulRegistrationPojoModel(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}

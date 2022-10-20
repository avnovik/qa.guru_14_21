package in.reqres.models.pojo;

public class UserRegistrationPojoModel {
    //{ "email": "eve.holt@reqres.in", "password": "pistol" }
    private String email;
    private String password;

    public UserRegistrationPojoModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

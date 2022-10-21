package in.reqres.models.pojo;

public class UserUnsuccessfulRegistrationPojoModel {
    //{ "error": "Missing password" }
    private String error;

    public UserUnsuccessfulRegistrationPojoModel(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

package in.reqres.models.lombok;

import lombok.Data;

@Data
public class UserLoginSuccessfulLombokModel {
    //{ "email": "eve.holt@reqres.in", "password": "cityslicka" }
    private String email;
    private String password;
}

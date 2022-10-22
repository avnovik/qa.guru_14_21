package in.reqres.models.lombok;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListUserInfoResponseLombokModel {
    public Integer id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;
}

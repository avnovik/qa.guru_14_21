package in.reqres.models.pojo;

public class ResponseUserUpdatePojoModel extends UserUpdatePojoModel{
    //{ "name": "morpheus", "job": "zion resident", "updatedAt": "2022-10-21T10:36:53.631Z" }
    private  String updatedAt;

    public ResponseUserUpdatePojoModel(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

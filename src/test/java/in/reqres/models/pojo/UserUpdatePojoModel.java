package in.reqres.models.pojo;

public class UserUpdatePojoModel {
    //{ "name": "morpheus", "job": "zion resident" }
    private String name;
    private String job;

    public UserUpdatePojoModel(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}

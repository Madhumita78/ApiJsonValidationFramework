package pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private int id;
    private String firstName;
    private String email;

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
}

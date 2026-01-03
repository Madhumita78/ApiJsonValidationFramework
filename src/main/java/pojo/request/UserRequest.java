package pojo.request;

import enums.Designation;

public class UserRequest {

    private String firstName;
    private String address;
    private Designation designation;
    private String email;
    private String phone;

    public UserRequest() {}

    public UserRequest(String firstName, String address,
                       Designation designation,
                       String email, String phone) {
        this.firstName = firstName;
        this.address = address;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() { return firstName; }
    public String getAddress() { return address; }
    public Designation getDesignation() { return designation; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setAddress(String address) { this.address = address; }
    public void setDesignation(Designation designation) { this.designation = designation; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}

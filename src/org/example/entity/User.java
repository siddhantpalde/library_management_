package org.example.entity;

public class User {
    private String userId;
    private String username;
    private String password;
    private UserType role;
    private String emailId;

    public User(String username, String password, UserType role, String emailId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.emailId = emailId;
    }
    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString(){
        return
                "User Id : "+this.getUserId()+
                        " Name : "+this.getUsername()
                        +" Email ID : " +this.getEmailId()
                        +" User Role : "+this.getRole();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId(){return this.userId;};

}

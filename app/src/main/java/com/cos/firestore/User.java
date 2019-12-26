package com.cos.firestore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private String uid;
    private String email;
    private String password;
    private String username;
    private String role;

    @Builder
    public User(String uid, String email, String password, String username, String role) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

package com.TaskForge.taskForge.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    private String id;

    private String name;

   @Indexed(unique = true)
    private String email;

    private String password;

    private Set<Role> roles;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
}

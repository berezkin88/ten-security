package me.birch.businessserver.models;

import lombok.Data;

@Data
public class User {

    private String username;
    private String password;
    private String code;
}

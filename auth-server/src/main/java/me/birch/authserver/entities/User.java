package me.birch.authserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "public")
public class User {

    @Id
    private String username;
    private String password;
}

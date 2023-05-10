package kz.sweet.fit.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import kz.sweet.fit.models.enums.Sex;
import kz.sweet.fit.util.UniqueElementsConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty")
    @UniqueElementsConstraint(message = "Username already taken, please choose another one")
    private String username;
    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "sex")
    private Sex sex;
    @Column(name = "birth")
    private LocalDateTime birth;
    @Column(name = "height")
    private Float height;
    @Column(name = "weight")
    private Float weight;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(String username, String password, String name, String lastname, Sex sex, LocalDateTime birth) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.sex = sex;
        this.birth = birth;
    }
}

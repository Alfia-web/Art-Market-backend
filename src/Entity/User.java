package com.example.demo.entity;
import jakarta.persistence.*;
import com.example.demo.entity.Passport;
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Passport passport;

    public Passport getPassport(){
        return passport;
    }

    public void setPassport(Passport passport){
        this.passport = passport;
    }


    private String fullName;
    private String email;
    private String phone;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

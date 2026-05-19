package com.example.demo.entity;
import jakarta.persistence.*;
import com.example.demo.entity.User;
import java.time.LocalDate;

@jakarta.persistence.Entity
@Table(name="passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;
    private String lastName;
    private String name;
    private String surname;
    private LocalDate birthDay;
    private String series;
    private String number;
    private LocalDate issuedDate;
    private String registartion;
    private String departmentCode;
    private String gender;
    private String departmentName;

    public String getName(String bame) {
        return name;
    }
    public void setName() {
        this.name = name;
    }

    public String getSurname(String surname) {
        return lastName;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName(String lastName) {
        return lastName;
    }
    public void setLastName() {
        this.lastName =  lastName;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getSeries() {
        return series;
    }
    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssuedBy() {
        return departmentName;
    }
    public void setIssuedBy(String issuedBy) {
        this.departmentName = issuedBy;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }
    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDivisionCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public LocalDate getBirthDate() {
        return birthDay;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDay = birthDate;
    }

    public String getRegistartion() {
        return registartion;
    }
    public void setRegistartion(String registartion) {
        this.registartion = registartion;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }


    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }
}

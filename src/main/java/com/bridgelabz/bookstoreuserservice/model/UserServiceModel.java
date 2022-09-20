package com.bridgelabz.bookstoreuserservice.model;

import com.bridgelabz.bookstoreuserservice.dto.UserServiceDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Purpose : UserServiceModel Are Used Create A table and connection to Database
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
@Entity
@Table(name = "users")
public class UserServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    private String emailId;
    private String password;
    private LocalDate dateOfBirth;
    private String kyc;
    private Long otp;
    private Boolean verify;
    private Date purchaseDate;
    private Date expireDate;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public UserServiceModel() {
    }

    public UserServiceModel(UserServiceDTO userServiceDTO) {
        this.firstName = userServiceDTO.getFirstName();
        this.lastName = userServiceDTO.getLastName();
        this.emailId = userServiceDTO.getEmailId();
        this.password = userServiceDTO.getPassword();
        this.dateOfBirth = userServiceDTO.getDateOfBirth();
    }
}


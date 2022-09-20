package com.bridgelabz.bookstoreuserservice.repository;

import com.bridgelabz.bookstoreuserservice.model.UserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Purpose : UserServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface UserServiceRepository extends JpaRepository<UserServiceModel,Long> {
    Optional<UserServiceModel> findByEmailId(String emailId);
}

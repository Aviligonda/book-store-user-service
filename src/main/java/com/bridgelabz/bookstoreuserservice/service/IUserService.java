package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.dto.UserServiceDTO;
import com.bridgelabz.bookstoreuserservice.model.UserServiceModel;
import com.bridgelabz.bookstoreuserservice.util.Response;

import java.util.List;
/**
 * Purpose : IUserService to Show The all APIs
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */

public interface IUserService {
    Response createUser(UserServiceDTO userServiceDTO);

    Response updateUser(UserServiceDTO userServiceDTO, String token, Long id);

    List<UserServiceModel> getAllUsers(String token);

    Response loginUser(String emailId, String password);


    Response resetChangePassword(String newPassword, String token);

    Response resetPassword(String emailId);

    Response deleteUser(Long id, String token);


    Response userVerification(String token);

    Response sendOTP(String token);

    Response verifyOTP(String token, Long otp);

    Response purchaseSubscription(String token);
}

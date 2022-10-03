package com.bridgelabz.bookstoreuserservice.controller;

import com.bridgelabz.bookstoreuserservice.dto.UserServiceDTO;
import com.bridgelabz.bookstoreuserservice.model.UserServiceModel;
import com.bridgelabz.bookstoreuserservice.service.IUserService;
import com.bridgelabz.bookstoreuserservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Purpose :REST ApIs Controller
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@RestController
@RequestMapping("/userService")
public class UserServiceController {
    @Autowired
    IUserService userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello Srinivas";
    }

    /**
     * Purpose : User Details Create
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : userServiceDTO
     */
    @PostMapping("/create")
    public ResponseEntity<Response> creatingUser( @RequestBody UserServiceDTO userServiceDTO) {
        Response response = userService.createUser(userServiceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Existing User Details Update
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,userServiceDTO and id
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id,
                                               @RequestHeader String token,
                                               @Valid @RequestBody UserServiceDTO userServiceDTO) {
        Response response = userService.updateUser(userServiceDTO, token, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Retrieve All User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<?>> getAllUsers(@RequestHeader String token) {
        List<UserServiceModel> response = userService.getAllUsers(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Login  with admin Email and Password
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : emailId and password
     */
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestParam String emailId,
                                              @RequestHeader String password) {
        Response response = userService.loginUser(emailId, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Reset Login Password
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token and password
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<Response> resetChangePassword(@RequestParam String newPassword,
                                                        @RequestHeader String token) {
        Response response = userService.resetChangePassword(newPassword, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Reset Login Password
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : emailId
     */
    @PostMapping("/forgetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : permanentDelete user Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token and id
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id,
                                               @RequestHeader String token) {
        Response response = userService.deleteUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Verify the User
     *
     * @author :Aviligonda Sreenivasulu
     * @Param : token
     */
    @GetMapping("/userVerification/{token}")
    public Response userVerification(@PathVariable String token) {
        return userService.userVerification(token);
    }

    /**
     * Purpose : Send OTP to  User
     *
     * @author :Aviligonda Sreenivasulu
     * @Param : token
     */
    @PostMapping("/sendOTP")
    public ResponseEntity<Response> sendOTP(@RequestHeader String token) {
        Response response = userService.sendOTP(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Verify OTP
     *
     * @author :Aviligonda Sreenivasulu
     * @Param : token
     */
    @PostMapping("/verifyOTP")
    public ResponseEntity<Response> verifyOTP(@RequestHeader String token,
                                              @RequestParam Long otp) {
        Response response = userService.verifyOTP(token, otp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose : Purchase Subscription
     *
     * @author :Aviligonda Sreenivasulu
     * @Param : token
     */
    @PutMapping("/purchaseSubscription")
    public ResponseEntity<Response> purchaseSubscription(@RequestHeader String token) {
        Response response = userService.purchaseSubscription(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.dto.UserServiceDTO;
import com.bridgelabz.bookstoreuserservice.exception.UserException;
import com.bridgelabz.bookstoreuserservice.model.UserServiceModel;
import com.bridgelabz.bookstoreuserservice.repository.UserServiceRepository;
//import com.bridgelabz.bookstoreuserservice.util.Email;
//import com.bridgelabz.bookstoreuserservice.util.MessageProducer;
import com.bridgelabz.bookstoreuserservice.util.Response;
import com.bridgelabz.bookstoreuserservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Purpose : AdminService to Implement the Business Logic
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Service
public class UserService implements IUserService {
    @Autowired
    MailService mailService;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserServiceRepository userServiceRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
//    @Autowired
//    Email email;
//    @Autowired
//    MessageProducer messageProducer;


    /**
     * Purpose : Implement the Logic of Creating User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  userServiceDTO
     */
    @Override
    public Response createUser(UserServiceDTO userServiceDTO) {
        UserServiceModel userServiceModel = new UserServiceModel(userServiceDTO);
        userServiceModel.setCreatedTime(LocalDateTime.now());
        userServiceModel.setPassword(passwordEncoder.encode(userServiceDTO.getPassword()));
        userServiceModel.setCreatedTime(LocalDateTime.now());
        userServiceModel.setVerify(false);
        userServiceRepository.save(userServiceModel);
        String userId = tokenUtil.createToken(userServiceModel.getId());
        String url="http://localhost:8080/userService/userVerification/"+userId;
        String body = "User Added Successfully with user id is :" + userServiceModel.getId()+
                "\n Click this Link to verify the User "+url;
        String subject = "User Registration Successfully";
        mailService.send(userServiceModel.getEmailId(), body, subject);
//        email.setTo(userServiceDTO.getEmailId());
//        email.setFrom(System.getenv("Email"));
//        email.setSubject("Verification");
//        email.setBody("registration Success");
//        messageProducer.sendMessage(email);
        return new Response(200, "Success", userServiceModel);
    }


    /**
     * Purpose : Implement the Logic of Update User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,id and userServiceDTO
     */
    @Override
    public Response updateUser(UserServiceDTO userServiceDTO, String token, Long id) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setFirstName(userServiceDTO.getFirstName());
                isIdPresent.get().setLastName(userServiceDTO.getLastName());
                isIdPresent.get().setEmailId(userServiceDTO.getEmailId());
                isIdPresent.get().setDateOfBirth(userServiceDTO.getDateOfBirth());
                isIdPresent.get().setUpdatedTime(LocalDateTime.now());
                userServiceRepository.save(isIdPresent.get());
                String body = "User Updated Successfully with user id is :" + isIdPresent.get().getId();
                String subject = "User Updated Successfully";
                mailService.send(isIdPresent.get().getEmailId(), body, subject);
                return new Response(200, "Success", isIdPresent.get());
            }
            throw new UserException(400, "No User Found with this id");
        }
        throw new UserException(400, "Token is Wrong");
    }

    /**
     * Purpose : Implement the Logic of Get All User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     */
    @Override
    public List<UserServiceModel> getAllUsers(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            List<UserServiceModel> isUsersPresent = userServiceRepository.findAll();
            if (isUsersPresent.size() > 0) {
                return isUsersPresent;
            }
            throw new UserException(400, "No users Found");
        }
        throw new UserException(400, "Token is Wrong");
    }

    /**
     * Purpose : Implement the Logic of Login credentials
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  emailId and password
     */
    @Override
    public Response loginUser(String emailId, String password) {
        Optional<UserServiceModel> isEmailPresent = userServiceRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            if (passwordEncoder.matches(password, isEmailPresent.get().getPassword())) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new Response(200, "Login Success", token);
            }
            throw new UserException(400, "Wrong Password");
        }
        throw new UserException(400, "Not found this EmailId");
    }


    /**
     * Purpose : Implement the Logic of Reset Password
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and password
     */
    @Override
    public Response resetChangePassword(String newPassword, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            isUserPresent.get().setPassword(passwordEncoder.encode(token));
            userServiceRepository.save(isUserPresent.get());
            return new Response(200, "Success", isUserPresent.get());
        }
        throw new UserException(400, "Email is not found");
    }

    /**
     * Purpose : Implement the Logic of Reset Password
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  emailId
     */
    @Override
    public Response resetPassword(String emailId) {
        Optional<UserServiceModel> isEmailPresent = userServiceRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String subject = "Reset Password";
            String body = " Reset password Use this link \n" + url + "\n Use this token ,Set  newPassword\n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            userServiceRepository.save(isEmailPresent.get());
            return new Response(200, "Success", "Check Your Register Mail");
        }
        throw new UserException(400, "Email is not found");
    }


    /**
     * Purpose : Implement the Logic of PermanentDelete User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     */
    @Override
    public Response deleteUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                userServiceRepository.delete(isIdPresent.get());
                return new Response(200, "Success", isIdPresent.get());
            }
            throw new UserException(400, "The User Not in delete ");
        }
        throw new UserException(400, "Not found with this id");
    }

    /**
     * Purpose : Implement the Logic of  User Verification
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     */
    @Override
    public Response userVerification(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            return new Response(200, "User Present", isUserPresent.get());
        }
        throw new UserException(400, "User Not Found");
    }

    /**
     * Purpose : Implement the Logic of Send OTP To User Details
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     */
    @Override
    public Response sendOTP(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            long min = 100000, max = 999999;
            long OTP = (int) ((Math.random() * (max - min)) + min);
            isUserPresent.get().setOtp(OTP);
            userServiceRepository.save(isUserPresent.get());
            String body = "Don't Share Anyone This OTP =" + OTP;
            String subject = "OTP Send Successfully";
            mailService.send(isUserPresent.get().getEmailId(), body, subject);
            return new Response(200, "SuccessFully Send OTP", isUserPresent.get());
        }
        throw new UserException(400, "Token is Wrong");
    }

    /**
     * Purpose : Implement the Logic of Verify the OTP
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     */
    @Override
    public Response verifyOTP(String token, Long otp) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            if (isUserPresent.get().getOtp().equals(otp)) {
                isUserPresent.get().setVerify(true);
                return new Response(200, "Success", isUserPresent.get());
            }
            throw new UserException(400, "OTP is Wrong");
        }
        throw new UserException(400, "Token is Wrong");
    }
    /**
     * Purpose : Implement the Logic of Subscription Date and Expire Date
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     */
    @Override
    public Response purchaseSubscription(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            isUserPresent.get().setPurchaseDate(new Date());
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 12);
            Date expireDate = calendar.getTime();
            isUserPresent.get().setExpireDate(expireDate);
            userServiceRepository.save(isUserPresent.get());
            return new Response(200, "Success", isUserPresent.get());
        }
        throw new UserException(400, "Token is Wrong");
    }
}


package com.bridgelabz.bookstoreuserservice.config;

import com.bridgelabz.bookstoreuserservice.model.UserServiceModel;
import com.bridgelabz.bookstoreuserservice.repository.UserServiceRepository;
import com.bridgelabz.bookstoreuserservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Purpose :Sheduling Configuration
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */

@Component
public class ShedulingConfig {
    @Autowired
    UserServiceRepository userServiceRepository;
    @Autowired
    MailService mailService;

    @Scheduled(fixedDelay = 60000)
    public void emailShedulingJob() {
        List<UserServiceModel> usersList = userServiceRepository.findAll();
        for (UserServiceModel userServiceModel : usersList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userServiceModel.getPurchaseDate());
            calendar.add(Calendar.MONTH, 11);
            Date date = calendar.getTime();
            if (new Date().equals(date)) {
                String body = "Your Subscription  expired in One Month:" + userServiceModel.getId();
                String subject = "Please take Subscription";
                mailService.send(userServiceModel.getEmailId(), body, subject);
            }
            Calendar calendar1 = Calendar.getInstance();
            calendar.setTime(userServiceModel.getPurchaseDate());
            calendar.add(Calendar.MONTH, 12);
            Date date1 = calendar1.getTime();
            if (userServiceModel.getExpireDate() == date1) {
                String body = "Your Subscription  expired :" + userServiceModel.getId();
                String subject = "Please take Subscription";
                mailService.send(userServiceModel.getEmailId(), body, subject);
            }

        }
    }
}

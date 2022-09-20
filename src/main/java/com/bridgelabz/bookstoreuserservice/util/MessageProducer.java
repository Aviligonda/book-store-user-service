//package com.bridgelabz.bookstoreuserservice.util;
//
//import com.bridgelabz.bookstoreuserservice.config.RabbitMqConfig;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//
//@Component
//public class MessageProducer {
//    /**
//     *
//     *  Purpose:SENDING MESSAGE TO RABBITMQ
//     *
//     * @author Aviligonda Sreenivasulu
//     *  @version 1.0
//     *
//     */
//
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendMessage(Email email) {
//        byte[] emailString = email.toString().getBytes();
//
//        System.out.println(new Date());
//        System.out.println();
//        rabbitTemplate.convertAndSend(RabbitMqConfig.ROUTING_KEY, email);
//        System.out.println("Is Producer returned ::: "+rabbitTemplate.isReturnListener());
//        System.out.println(email);
//        System.out.println(new Date());
//    }
//}

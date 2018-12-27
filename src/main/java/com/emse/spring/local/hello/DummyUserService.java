package com.emse.spring.local.hello;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DummyUserService implements UserService {

    @Autowired public GreetingService greeting;



    public void greetAll(){
        String A = "Elodie";
        String B = "Charles";
        greeting.greet(A);
        greeting.greet(B);


    }




}

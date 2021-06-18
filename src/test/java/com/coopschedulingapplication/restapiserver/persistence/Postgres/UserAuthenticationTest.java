package com.coopschedulingapplication.restapiserver.persistence.Postgres;


import com.coopschedulingapplication.restapiserver.Data.Enums.UserType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class UserAuthenticationTest {

    @Autowired
    private UserAuthentication UA;

    @Nested
    class sunshine{
        @Test
        void chainUser(){
            assertEquals("wrong id",0,UA.authenticate("chain","chain", UserType.chain_administrator).getResult());
        }

        @Test
        void storeUser(){
            assertEquals("wrong id",1,UA.authenticate("store","store", UserType.store_administrator).getResult());
        }

        @Test
        void workerUser(){
            assertEquals("wrong id",2,UA.authenticate("worker","worker", UserType.worker).getResult());
        }
    }

    @Nested
    class rain{
        @Test
        void wrongEmail(){
            assertEquals("wrong error or no error", "email does not exist",UA.authenticate("wrong","chain", UserType.chain_administrator).getError());
        }

        @Test
        void wrongPassword(){
            assertEquals("wrong error or no error", "password does not match email",UA.authenticate("chain","wrong", UserType.chain_administrator).getError());
        }

        @Test
        void wrongUserType(){
            assertEquals("wrong error or no error", "user has insufficient access",UA.authenticate("chain","chain", UserType.store_administrator).getError());
        }
    }
}

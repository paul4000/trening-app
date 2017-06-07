package com.example.assistant.workout_assistant.authorization;

import com.auth0.android.jwt.JWT;

import java.util.Date;
import java.util.List;

public class Authorization {

    public boolean checkIfLogged(String token) {
        if (token == null) {
            return false;
        }

        JWT jwt = new JWT(token);

        String issuer = jwt.getIssuer();
        System.out.println(issuer);

        String subject = jwt.getSubject();
        System.out.println(issuer);

        List<String> audience = jwt.getAudience();
        if (audience != null) {
            audience.forEach(System.out::println);
        }

        Date expiresAt = jwt.getExpiresAt();
        System.out.println(expiresAt);

        return false;
    }

}

package kimjang.toolkit.solsol.jwt;

import kimjang.toolkit.solsol.config.jwt.JWTTokenValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenValidateTest {
    @Test
    public void jwtTest(){
        String testJwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTb2wtS2ltamFuZyIsInN1YiI6IkpXVCBUb2tlbiIsImVtYWlsIjoic2V1bmdoeW83NzQyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0NzYxOTc1LCJleHAiOjE3MjQ3NjI1NzV9.3Xl0kSuSqswqM_YZqL0vpE-GlZRLe0FPf30MoQP8nHg";
        JWTTokenValidator validator = new JWTTokenValidator();
        String email = validator.getEmail(testJwt);
        System.out.println(email);
    }
}
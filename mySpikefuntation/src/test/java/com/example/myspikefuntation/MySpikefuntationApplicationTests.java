package com.example.myspikefuntation;




import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.cert.CertificateEncodingException;

@SpringBootTest
class MySpikefuntationApplicationTests {

    @Test
   public void contextLoads() {
        String password = "fdsfd";
        String encode = MD5Encoder.encode(password.getBytes());
        System.out.println(encode);
    }

}

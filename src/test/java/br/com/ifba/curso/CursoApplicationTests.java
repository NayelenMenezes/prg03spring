package br.com.ifba.curso; 

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CursoApplicationTests {

    static {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void contextLoads() {
    }

}
package tiw1.SpringBootTP3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.beans.Beans;

@SpringBootApplication
@Import(value = Beans.class)
public class SpringBootTp3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTp3Application.class, args);
    }
}

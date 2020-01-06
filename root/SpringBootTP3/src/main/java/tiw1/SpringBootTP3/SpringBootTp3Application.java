package tiw1.SpringBootTP3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tiw1.SpringBootTP3.persistence.TrottinetteLoader;

import javax.annotation.PostConstruct;
import java.beans.Beans;

@SpringBootApplication
@Import(value = Beans.class)
public class SpringBootTp3Application {

	@PostConstruct
	public void load() throws Exception {
		TrottinetteLoader.load();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTp3Application.class, args);
	}

}

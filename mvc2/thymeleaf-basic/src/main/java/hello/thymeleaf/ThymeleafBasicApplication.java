package hello.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@RequestMapping("/basic")
public class ThymeleafBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafBasicApplication.class, args);
	}

}

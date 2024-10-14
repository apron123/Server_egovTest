package egovframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ServletComponentScan
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(WebApplication.class);
		// springApplication.setBannerMode(Banner.Mode.OFF);
		// springApplication.setLogStartupInfo(false);
		springApplication.run(args);
	}

}

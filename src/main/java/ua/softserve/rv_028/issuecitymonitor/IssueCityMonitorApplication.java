package ua.softserve.rv_028.issuecitymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class IssueCityMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueCityMonitorApplication.class, args);
	}

}

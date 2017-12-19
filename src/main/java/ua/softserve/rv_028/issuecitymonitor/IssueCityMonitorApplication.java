package ua.softserve.rv_028.issuecitymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IssueCityMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueCityMonitorApplication.class, args);
	}

}

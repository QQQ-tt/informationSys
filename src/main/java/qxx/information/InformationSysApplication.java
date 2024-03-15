package qxx.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author qtx
 */
@ServletComponentScan
@SpringBootApplication
public class InformationSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationSysApplication.class, args);
    }

}

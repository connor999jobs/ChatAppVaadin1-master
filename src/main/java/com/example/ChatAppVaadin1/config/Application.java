package com.example.ChatAppVaadin1.config;

import com.vaadin.flow.component.dependency.NpmPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
public class Application extends SpringBootServletInitializer {

}

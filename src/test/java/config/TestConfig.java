package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import main.Application;

@Configuration
@Import(Application.class)
public class TestConfig {
  // Any additional configuration beans
}
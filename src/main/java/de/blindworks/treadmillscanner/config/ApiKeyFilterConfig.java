package de.blindworks.treadmillscanner.config;

import de.blindworks.treadmillscanner.security.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyFilterConfig {

  @Bean
  public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter(
      @Value("${treadmill.api-key}") String apiKey) {
    FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ApiKeyFilter(apiKey));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }
}

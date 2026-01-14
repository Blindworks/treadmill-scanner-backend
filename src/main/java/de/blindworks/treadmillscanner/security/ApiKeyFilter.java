package de.blindworks.treadmillscanner.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyFilter extends OncePerRequestFilter {

  private static final String API_KEY_HEADER = "X-API-Key";
  private final String expectedApiKey;

  public ApiKeyFilter(String expectedApiKey) {
    this.expectedApiKey = expectedApiKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestUri = request.getRequestURI();
    if (!requestUri.startsWith("/api/")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (isPreflightRequest(request)) {
      addCorsHeaders(request, response);
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    String apiKey = request.getHeader(API_KEY_HEADER);
    if (!StringUtils.hasText(expectedApiKey) || !expectedApiKey.equals(apiKey)) {
      addCorsHeaders(request, response);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean isPreflightRequest(HttpServletRequest request) {
    return "options".equalsIgnoreCase(request.getMethod());
  }

  private void addCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
    String origin = request.getHeader("Origin");
    response.setHeader("Access-Control-Allow-Origin", StringUtils.hasText(origin) ? origin : "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    String requestHeaders = request.getHeader("Access-Control-Request-Headers");
    response.setHeader(
        "Access-Control-Allow-Headers", StringUtils.hasText(requestHeaders) ? requestHeaders : "*");
    response.setHeader("Vary", "Origin, Access-Control-Request-Headers");
  }
}

package com.codigo.demo.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codigo.demo.domain.repository.UserMasterRepository;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

	@Autowired
	private UserMasterRepository userRepo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.ignore.uri}")
	private String[] ignoreUri;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			String jwtToken = parseJwt(request);
			if (jwtToken == null) {
				createResposenData(response, HttpStatus.UNAUTHORIZED, "Invalid Token!");
			}

			// Add an condition to know given token is expire or not
			try {
				if (this.jwtTokenUtil.isTokenExpired(jwtToken)) {
					createResposenData(response, HttpStatus.FORBIDDEN, "Token Expired");
				}
			} catch (ExpiredJwtException exj) {
				createResposenData(response, HttpStatus.FORBIDDEN, "Token Expired");
			}
			var username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
			String[] usernames = username.split(",");
			var userDetails = userRepo.findUserMasterByLoginName(usernames[1]);

			if (!this.jwtTokenUtil.validateToken(jwtToken,
					userDetails.get().getId() + "," + userDetails.get().getLoginName())) {
				createResposenData(response, HttpStatus.UNAUTHORIZED, "Invalid Token!");
			}

			var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createResposenData(response, HttpStatus.UNAUTHORIZED, "Invalid Token!");
			//return;
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		var uri = request.getRequestURI();

		boolean hasWebjar = uri.indexOf("webjars") != -1;
		boolean hasSwagger = uri.indexOf("swagger-resources") != -1;
		boolean hasActuator = uri.indexOf("actuator") != -1;

		return Arrays.asList(ignoreUri).contains(uri) || hasWebjar || hasSwagger || hasActuator;

	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private void createResposenData(HttpServletResponse response, HttpStatus responseStatus, String errorMsg)
			throws IOException {

		var map = new HashMap<>();
		map.put("message", errorMsg);
		map.put("code", responseStatus.value());

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(responseStatus.value());
		out.print(JsonUtils.prettyJSON(map));
		out.flush();
		out.close();
		response.sendError(responseStatus.value(), errorMsg);
	}

}

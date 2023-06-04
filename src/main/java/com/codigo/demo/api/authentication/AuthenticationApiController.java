package com.codigo.demo.api.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.codigo.demo.annotation.APIVersion;
import com.codigo.demo.api.authentication.Request.AuthenticationReq;
import com.codigo.demo.api.authentication.Request.UserRequest;
import com.codigo.demo.api.authentication.Response.AuthenticationRes;
import com.codigo.demo.domain.entity.UserMaster;
import com.codigo.demo.domain.service.UserService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.handler.ResponseSuccessHandler;
import com.codigo.demo.utils.JsonUtils;
import com.codigo.demo.utils.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@APIVersion
@Api(tags = "Authentication", value = "Authentication Api")
public class AuthenticationApiController implements AuthenticationApiConstant {

	private Logger logger = LoggerFactory.getLogger(AuthenticationApiController.class);
	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@Autowired
	public AuthenticationApiController(final AuthenticationManager authManager, final JwtTokenUtil jwtUtil,
			final PasswordEncoder passwordEncoder, final UserService userService) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@CrossOrigin("*")
	@PostMapping(value = LOGIN)
	public ResponseEntity<String> login(@RequestBody AuthenticationReq request) {
		try {
			logger.info("Authentication request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getLoginName(), request.getPassword()));

			var user = (UserMaster) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthenticationRes response = new AuthenticationRes(user.getLoginName(), accessToken);
			logger.info("Authentication response : {}", JsonUtils.toJSON(response));
			res.setBody(response);
			return res.response();

		} catch (ApiException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to login user", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PostMapping(value = REGISTER)
	@ApiOperation(value = "Register User", notes = "Register user api")
	public ResponseEntity<String> registerUser(@RequestBody(required = true) UserRequest request) throws Exception {
		try {
			logger.info("Register User request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();
			var password = this.passwordEncoder.encode(request.getPassword());
			var userSR = this.userService.save(request.getName(), password, request.getLoginName(), request.getPhone(),
					request.getEmail());
			if (!userSR.isStatus()) {
				throw new ApiException("Fail to register user", "400", userSR.getMessage());
			}
			res.setBody(userSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to register user", "400", ex.getMessage());
		}
	}
}

package com.codigo.demo.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codigo.demo.annotation.APIVersion;
import com.codigo.demo.domain.service.UserService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.handler.ResponseSuccessHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@APIVersion
@Api(tags = "User", value = "User Api")
public class UserApiController implements UserApiConstant {

	private final UserService userService;

	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserApiController(final UserService userService, final BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@CrossOrigin("*")
	@GetMapping(value = USER_BY_ID)
	@ApiOperation(value = "Retrieve User By User Id", notes = "Retrieve user by user id api")
	public ResponseEntity<String> getUserById(@PathVariable("id") Long id) throws Exception {
		try {
			var res = new ResponseSuccessHandler();
			var userSR = this.userService.getById(id);
			if (!userSR.isStatus()) {
				throw new ApiException("Fail to retrieve user", "400", userSR.getMessage());
			}
			res.setBody(userSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve user", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@GetMapping(value = USER)
	@ApiOperation(value = "Retrieve all users", notes = "Retrieve all users api")
	public ResponseEntity<String> getUsers(@RequestParam(required = true) Integer limit,
			@RequestParam(required = true) Integer offset) throws Exception {
		try {
			var res = new ResponseSuccessHandler();
			Pageable pageable = PageRequest.of((offset > 0) ? offset - 1 : 0, limit);
			
			var userSR = this.userService.findAll(pageable);
			if (!userSR.isStatus()) {
				throw new ApiException("Fail to retrieve all user", "400", userSR.getMessage());
			}
			res.setBody(userSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve all user", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PostMapping(value = USER)
	@ApiOperation(value = "Create User", notes = "Create user api")
	public ResponseEntity<String> saveUser(@RequestParam(required = true) String name,
			@RequestParam(required = true) String password, @RequestParam(required = true) String loginName,
			@RequestParam(required = true) String phone, @RequestParam(required = true) String email) throws Exception {
		var res = new ResponseSuccessHandler();
		try {
			password = this.passwordEncoder.encode(password);
			var userSR = this.userService.save(name, password, loginName, phone, email);
			if (!userSR.isStatus()) {
				throw new ApiException("Fail to save user", "400", userSR.getMessage());
			}
			res.setBody(userSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve user", "400", ex.getMessage());
		}
	}

}

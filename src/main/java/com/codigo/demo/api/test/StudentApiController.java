//package com.codigo.demo.api.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.codigo.demo.annotation.APIVersion;
//import com.codigo.demo.domain.entity.Student;
//import com.codigo.demo.domain.repository.StudentRepository;
//import com.codigo.demo.domain.service.UserService;
//import com.codigo.demo.exception.ApiException;
//import com.codigo.demo.handler.ResponseSuccessHandler;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Controller
//@APIVersion
//@Api(tags = "Student", value = "Student Api")
//public class StudentApiController {
//
//	private final UserService userService;
//
//	@Autowired
//	private StudentRepository studentRepository;
//
//	private final BCryptPasswordEncoder passwordEncoder;
//
//	@Autowired
//	public StudentApiController(final UserService userService, final BCryptPasswordEncoder passwordEncoder) {
//		this.userService = userService;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	@CrossOrigin("*")
//	@GetMapping(value = "student/{id}")
//	@ApiOperation(value = "Retrieve User By User Id", notes = "Retrieve user by user id api")
//	public ResponseEntity<String> getUserById(@PathVariable("id") Long id) throws Exception {
//		try {
//			var res = new ResponseSuccessHandler();
//			var userSR = this.studentRepository.findById("Eng2015001").get();
//
//			res.setBody(userSR);
//			return res.response();
//		} catch (ApiException ex) {
//			throw ex;
//		} catch (Exception ex) {
//			throw new ApiException("Fail to retrieve user", "400", ex.getMessage());
//		}
//	}
//
//	@CrossOrigin("*")
//	@PostMapping(value = "student")
//	@ApiOperation(value = "Retrieve User By User Id", notes = "Retrieve user by user id api")
//	public ResponseEntity<String> getUsers(@RequestParam(required = true) Integer limit,
//			@RequestParam(required = true) Integer offset) throws Exception {
//		try {
//			var res = new ResponseSuccessHandler();
//			Pageable pageable = PageRequest.of((offset > 0) ? offset - 1 : 0, limit);
//
//			Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
//			studentRepository.save(student);
//			res.setBody(Boolean.TRUE);
//			return res.response();
//		} catch (ApiException ex) {
//			throw ex;
//		} catch (Exception ex) {
//			throw new ApiException("Fail to retrieve user", "400", ex.getMessage());
//		}
//	}
//
//}

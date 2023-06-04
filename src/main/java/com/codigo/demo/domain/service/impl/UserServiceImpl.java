package com.codigo.demo.domain.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.entity.UserMaster;
import com.codigo.demo.domain.repository.UserMasterRepository;
import com.codigo.demo.domain.service.UserService;
import com.codigo.demo.utils.JsonUtils;

/**
 * @Author sandar linn
 * @CreatedAt June 3, 2023
 */

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserMasterRepository userRepository;

	@Autowired
	public UserServiceImpl(final UserMasterRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public ServiceResult<Long> save(String name, String password, String loginName, String phone, String email) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			UserMaster user = new UserMaster();
			user.setName(name);
			user.setLoginName(loginName);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhone(phone);
			user = this.userRepository.save(user);
			result.success(user.getId());
		} catch (Exception e) {
			result.fail(e, e.getMessage());
		}

		return result;
	}

	@Override
	public ServiceResult<Long> update(Long id, String name, String password, String loginName, String phone,
			String email) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			UserMaster user = new UserMaster(id, name, password, loginName, phone, email, new Date());
			user = this.userRepository.save(user);
			result.success(user.getId());
		} catch (Exception e) {
			result.fail(e, e.getMessage());
		}

		return result;
	}

	@Override
	public ServiceResult<UserMaster> getById(Long id) {
		ServiceResult<UserMaster> result = new ServiceResult<>();
		try {
			var user = this.userRepository.findById(id);
			result.success(user.get());
		} catch (Exception e) {
			result.fail(e, e.getMessage());
		}

		return result;
	}

	@Override
	public ServiceResult<Page<UserMaster>> findAll(Pageable pageable) {
		ServiceResult<Page<UserMaster>> result = new ServiceResult<>();
		try {
			var users = this.userRepository.findAllUser(pageable);
			logger.info("Retrieve users response : {}", JsonUtils.toJSON(users));
			result.success(users);
		} catch (Exception e) {
			logger.error("Error occur in retrieve users : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}

		return result;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserMaster user = null;
//		UserDetails userDetails;
//		try {
//			user = this.userRepository.findUserMasterByEmail(username).get();
//		} catch (Exception e) {
//			logger.info("Fail to load user : {}", user);
//		}
//
//		if (user != null) {
//			logger.info("user is : {}", user.toString());
//			userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//					null);
//
//		} else {
//			throw new UsernameNotFoundException(username);
//		}
//
//		logger.info("User detail is : {}", userDetails);
//		return userDetails;
//	}
}

package com.codigo.demo.api.evoucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.codigo.demo.annotation.APIVersion;
import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.dto.EVoucherReqDTO;
import com.codigo.demo.domain.service.EVoucherService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.handler.ResponseSuccessHandler;
import com.codigo.demo.utils.JsonUtils;
import com.codigo.demo.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@APIVersion
@Api(tags = "E-Voucher", value = "E-Voucher Api")
public class EVoucherApiController implements EVoucherApiConstant {

	private Logger logger = LoggerFactory.getLogger(EVoucherApiController.class);

	private final EVoucherService eVoucherService;

	public EVoucherApiController(final EVoucherService eVoucherService) {
		this.eVoucherService = eVoucherService;
	}

	@CrossOrigin("*")
	@PostMapping(value = EVOUCHER)
	@ApiOperation(value = "Create E-Voucher", notes = "Create e-voucher api")
	public ResponseEntity<String> saveEVoucher(@RequestBody(required = true) EVoucherReqDTO request) throws Exception {
		try {
			logger.info("Create e-coucher request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();

			var evoucherSR = this.eVoucherService.save(request);
			logger.info("Create e-coucher response : {}", JsonUtils.toJSON(evoucherSR.getResult()));
			if (!evoucherSR.isStatus()) {
				throw new ApiException("Fail to create e-voucher api", "400", evoucherSR.getMessage());
			}

			res.setBody(evoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to create e-voucher : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to create e-voucher", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PutMapping(value = EVOUCHER)
	@ApiOperation(value = "Update E-Voucher", notes = "Update e-voucher api")
	public ResponseEntity<String> updateEVoucher(@RequestBody(required = true) EVoucherReqDTO request)
			throws Exception {
		try {
			logger.info("Update e-coucher request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();

			var evoucherSR = this.eVoucherService.update(request);
			logger.info("Update e-coucher response : {}", JsonUtils.toJSON(evoucherSR.getResult()));
			if (!evoucherSR.isStatus()) {
				throw new ApiException("Fail to update e-voucher", "400", evoucherSR.getMessage());
			}

			res.setBody(evoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to update e-voucher : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to update e-voucher", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@GetMapping(value = EVOUCHER)
	@ApiOperation(value = "Retrieve All E-Voucher", notes = "Retrieve e-voucher api")
	public ResponseEntity<String> deleteEVoucherById(@RequestParam(required = false) String title,
			@RequestParam(required = true) Integer limit, @RequestParam(required = true) Integer offset)
			throws Exception {
		try {
			logger.info("Retrieve e-voucher request : {}", JsonUtils.toJSON(title));
			var res = new ResponseSuccessHandler();

			Pageable pageable = PageRequest.of((offset > 0) ? offset - 1 : 0, limit);
			title = StringUtils.isNullOrEmpty(title) ? null : title;
			var eVoucherSR = this.eVoucherService.retrieveAll(title, pageable);
			logger.info("Retrieve e-voucher response : {}", JsonUtils.toJSON(eVoucherSR.getResult()));
			if (!eVoucherSR.isStatus()) {
				throw new ApiException("Fail to retrieve e-voucher", "400", eVoucherSR.getMessage());
			}

			res.setBody(eVoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to retrieve e-voucher : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve e-voucher", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@GetMapping(value = EVOUCHER_BY_ID)
	@ApiOperation(value = "Retrieve E-Voucher by id", notes = "Retrieve e-voucher by id api")
	public ResponseEntity<String> getEVoucherById(@PathVariable(required = true) Long id) throws Exception {
		try {
			logger.info("Retrieve e-voucher by id request : {}", JsonUtils.toJSON(id));
			var res = new ResponseSuccessHandler();

			var eVoucherSR = this.eVoucherService.getById(id);
			logger.info("Retrieve e-voucher by id response : {}", JsonUtils.toJSON(eVoucherSR.getResult()));
			if (!eVoucherSR.isStatus()) {
				throw new ApiException("Fail to retrieve e-voucher by id", "400", eVoucherSR.getMessage());
			}

			res.setBody(eVoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to retrieve e-voucher by id : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve e-voucher by id", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@DeleteMapping(value = EVOUCHER_BY_ID)
	@ApiOperation(value = "Delete E-Voucher by id", notes = "Delete e-voucher by id api")
	public ResponseEntity<String> deleteEVoucherById(@PathVariable(required = true) Long id) throws Exception {
		try {
			logger.info("Delete e-voucher by id request : {}", JsonUtils.toJSON(id));
			var res = new ResponseSuccessHandler();

			var eVoucherSR = this.eVoucherService.delete(id);
			logger.info("Delete e-voucher by id response : {}", JsonUtils.toJSON(eVoucherSR.getResult()));
			if (!eVoucherSR.isStatus()) {
				throw new ApiException("Fail to delete e-voucher by id", "400", eVoucherSR.getMessage());
			}

			res.setBody(eVoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to delete e-voucher by id : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to delete e-voucher by id", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PutMapping(value = EVOUCHER_UPDATE_STATUS)
	@ApiOperation(value = "Update E-Voucher Status", notes = "Update e-voucher status api")
	public ResponseEntity<String> updateEVoucherStatus(@PathVariable(required = true) Long id,
			@RequestParam(required = true) EVoucherStatus status) throws Exception {
		try {
			logger.info("Update e-coucher id : {} and status : {}", id, status);
			var res = new ResponseSuccessHandler();

			var evoucherSR = this.eVoucherService.updateStatus(id, status);
			logger.info("Update e-coucher status response : {}", JsonUtils.toJSON(evoucherSR.getResult()));
			if (!evoucherSR.isStatus()) {
				throw new ApiException("Fail to update e-voucher status", "400", evoucherSR.getMessage());
			}

			res.setBody(evoucherSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to update e-voucher status: {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to update e-voucher status", "400", ex.getMessage());
		}
	}

}

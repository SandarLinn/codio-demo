package com.codigo.demo.api.estore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.codigo.demo.annotation.APIVersion;
import com.codigo.demo.domain.dto.EVoucherIssueReqDTO;
import com.codigo.demo.domain.dto.PromoDetailReqDTO;
import com.codigo.demo.domain.service.DiscountPerPaymentService;
import com.codigo.demo.domain.service.EVoucherIssueService;
import com.codigo.demo.domain.service.PromoDetailService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.handler.ResponseSuccessHandler;
import com.codigo.demo.utils.JsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@APIVersion
@Api(tags = "E-Store", value = "E-Store Api")
public class EStoreApiController implements EStoreApiConstant {

	private Logger logger = LoggerFactory.getLogger(EStoreApiController.class);

	private final DiscountPerPaymentService discountPerPaymentService;
	private final EVoucherIssueService eVoucherIssueService;
	private final PromoDetailService pormoDetailService;

	@Autowired
	public EStoreApiController(final DiscountPerPaymentService discountPerPaymentService,
			final EVoucherIssueService eVoucherIssueService, final PromoDetailService pormoDetailService) {
		this.discountPerPaymentService = discountPerPaymentService;
		this.eVoucherIssueService = eVoucherIssueService;
		this.pormoDetailService = pormoDetailService;
	}

	@CrossOrigin("*")
	@GetMapping(value = PAYMENTS)
	@ApiOperation(value = "Retrieve All Payments", notes = "Retrieve all payments api")
	public ResponseEntity<String> retrieveAllPyaments() throws Exception {
		try {
			logger.info("Retrieve all payments request");
			var res = new ResponseSuccessHandler();

			var paymentsSR = this.discountPerPaymentService.findAll();
			logger.info("Retrieve all payments response : {}", JsonUtils.toJSON(paymentsSR.getResult()));
			if (!paymentsSR.isStatus()) {
				throw new ApiException("Fail to retrieve all payments", "400", paymentsSR.getMessage());
			}

			res.setBody(paymentsSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to retrieve all payments : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to retrieve e-voucher", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PostMapping(value = EVOUCHER_ISSUE)
	@ApiOperation(value = "Purchase E-Voucher", notes = "Purchase e-voucher api")
	public ResponseEntity<String> purchaseEVoucher(@RequestBody(required = true) EVoucherIssueReqDTO request)
			throws Exception {
		try {
			logger.info("Purchase e-voucher request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();

			var eVoucherIssueSR = this.eVoucherIssueService.save(request);
			logger.info("Retrieve all payments response : {}", JsonUtils.toJSON(eVoucherIssueSR.getResult()));
			if (!eVoucherIssueSR.isStatus()) {
				throw new ApiException("Fail to purchase e-voucher", "400", eVoucherIssueSR.getMessage());
			}

			res.setBody(eVoucherIssueSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to purchase e-voucher : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to purchase e-voucher", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PostMapping(value = GENERATE_PROMOCODE)
	@ApiOperation(value = "Generate Promo Detail", notes = "Generate promo detail api")
	public ResponseEntity<String> generatePromoDetail(@RequestBody(required = true) PromoDetailReqDTO request,
			@RequestParam(required = true) Integer numberOfPromoCode) throws Exception {
		try {
			logger.info("Generate promo detail request : {}", JsonUtils.toJSON(request));
			var res = new ResponseSuccessHandler();

			var generatePromoSR = this.pormoDetailService.generatePromoCodes(request, numberOfPromoCode);
			logger.info("Generate promo detail response : {}", JsonUtils.toJSON(generatePromoSR.getResult()));
			if (!generatePromoSR.isStatus()) {
				throw new ApiException("Fail generate promo detail ", "400", generatePromoSR.getMessage());
			}

			res.setBody(generatePromoSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to generate promo detail  : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to generate promo detail ", "400", ex.getMessage());
		}
	}

	@CrossOrigin("*")
	@PostMapping(value = VALIDATE_PROMO_STATUS)
	@ApiOperation(value = "Validate Promo Code Status", notes = "Validate promo code status api")
	public ResponseEntity<String> validatePromoCodeStatus(@RequestParam(required = true) String promoCode) throws Exception {
		try {
			logger.info("Validate promo code status request : {}", JsonUtils.toJSON(promoCode));
			var res = new ResponseSuccessHandler();

			var validatePormoCodeSR = this.pormoDetailService.validatePromoCodeStatus(promoCode);
			logger.info("Validate promo code status response : {}", JsonUtils.toJSON(validatePormoCodeSR.getResult()));
			if (!validatePormoCodeSR.isStatus()) {
				throw new ApiException("Fail validate promo code status  ", "400", validatePormoCodeSR.getMessage());
			}

			res.setBody(validatePormoCodeSR.getResult());
			return res.response();
		} catch (ApiException ex) {
			logger.info("Fail to validate promo code status   : {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			throw new ApiException("Fail to validate promo code status  ", "400", ex.getMessage());
		}
	}

}

package com.codigo.demo.domain.service.schedular;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.entity.EVoucher;
import com.codigo.demo.domain.repository.EVoucherRepository;

@Service
public class EVoucherSchedular {

	private Logger logger = LoggerFactory.getLogger(EVoucherSchedular.class);

	private final EVoucherRepository eVoucherRepository;

	@Autowired
	public EVoucherSchedular(final EVoucherRepository eVoucherRepository) {
		this.eVoucherRepository = eVoucherRepository;
	}

	@Scheduled(cron = "${schedule.cron.eVoucher.checkStatus}")
	public void runCheckEVoucherStatusSchedule() {
		logger.info("start check e-voucher status schedule....");
		runCheckEVoucherStatusProcess();
		logger.info("start check e-voucher status schedule....");
	}

	/*
	 * retrieve active status 
	 * check if the expiry date is today then set status to EXPIRE
	 */
	public void runCheckEVoucherStatusProcess() {
		var eVouchers = this.eVoucherRepository.findEVoucherByStatus(EVoucherStatus.ACTIVE);
		for (EVoucher eVoucher : eVouchers) {
			if (eVoucher.getExpiryDate().after(new Date())) {
				eVoucher.setStatus(EVoucherStatus.EXPIRE);
				this.eVoucherRepository.save(eVoucher);
			}
		}

	}
}

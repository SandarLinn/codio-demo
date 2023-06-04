package com.codigo.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.entity.EVoucher;
import com.codigo.demo.domain.projection.EVoucherView;

@Repository
public interface EVoucherRepository extends JpaRepository<EVoucher, Long> {

	List<EVoucher> findEVoucherByStatus(EVoucherStatus status);

	@Query(value = "select ev.id ,buy_type as buyType, created_at as createdAt, description, expiry_date as expiryDate, gift_per_limit as gitfPerLimit,\r\n"
			+ "max_limit as maxLimit, name, phone, quantity, sold_quantity as soldQuantity, status, title,\r\n"
			+ " discount_per_payment_id as discountPerPaymentId, payment_method as paymentMethod, discount_percent as discountPercent,\r\n"
			+ " discount_amount as discountAmount\r\n" + " from e_voucher ev\r\n"
			+ " left outer join discount_per_payment dpp on dpp.id = ev.discount_per_payment_id\r\n"
			+ " where ev.id = :id", nativeQuery = true)
	Optional<EVoucherView> findEVoucherById(@Param(value = "id") Long id);

	@Query(value = "select ev.id ,buy_type as buyType, created_at as createdAt, description, expiry_date as expiryDate, gift_per_limit as gitfPerLimit,\r\n"
			+ "max_limit as maxLimit, name, phone, quantity, sold_quantity as soldQuantity, status, title,\r\n"
			+ " discount_per_payment_id as discountPerPaymentId, payment_method as paymentMethod, discount_percent as discountPercent,\r\n"
			+ " discount_amount as discountAmount\r\n" + " from e_voucher ev\r\n"
			+ " left outer join discount_per_payment dpp on dpp.id = ev.discount_per_payment_id\r\n"
			+ " where :title is null or title like concat('%',:title,'%') ", countQuery = "select count(*)\r\n"
					+ " from e_voucher ev\r\n"
					+ " left outer join discount_per_payment dpp on dpp.id = ev.discount_per_payment_id\r\n"
					+ " where :title is null or title like concat('%',:title,'%') ", nativeQuery = true)
	Page<EVoucherView> findEVoucherInfo(@Param(value = "title") String title, Pageable pageable);

}

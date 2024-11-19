package com.ecommerce.config;

import com.ecommerce.util.VNPayUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VNPAYConfig {

    @Getter
    @Value("${payment.vnPay.url}")
    private String vnp_PayUrl;

    @Getter
    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Getter
    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;

    @Getter
    @Value("${payment.vnPay.secretKey}")
    private String secretKey;

    @Getter
    @Value("${payment.vnPay.version}")
    private String vnp_Version;

    @Getter
    @Value("${payment.vnPay.command}")
    private String vnp_Command;

    @Getter
    @Value("${payment.vnPay.orderType}")
    private String orderType;

    private static final String TIMEZONE = "Asia/Ho_Chi_Minh";
    private static final String CURRENCY_CODE = "VND";
    private static final String LOCALE = "vn";

    public Map<String, String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", vnp_Version);
        vnpParamsMap.put("vnp_Command", vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", CURRENCY_CODE);
        vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: " + VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", orderType);
        vnpParamsMap.put("vnp_Locale", LOCALE);
        vnpParamsMap.put("vnp_ReturnUrl", vnp_ReturnUrl);

        // Tạo định dạng ngày giờ với timezone
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

        // Lấy thời gian hiện tại với timezone "Asia/Ho_Chi_Minh"
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);

        // Thêm 60 phút cho vnp_ExpireDate
        calendar.add(Calendar.MINUTE, 60);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnpParamsMap;
    }
}

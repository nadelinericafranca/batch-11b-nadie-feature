package xyz.mynt.bootcamp5.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class VoucherRemoteImpl implements VoucherRemote {

    private final RestTemplate restTemplate;

    @Value("${voucher.api}")
    private String VOUCHER_API;

    public VoucherRemoteImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VoucherResponse getVoucher(String voucher) throws RuntimeException {

        ResponseEntity<VoucherResponse> voucherResponse = null;
        try {
            voucherResponse = restTemplate.getForEntity(VOUCHER_API + voucher.trim(), VoucherResponse.class);
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAs(VoucherResponse.class);
        }

        return voucherResponse.getBody();
    }
}

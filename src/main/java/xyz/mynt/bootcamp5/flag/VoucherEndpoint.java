package xyz.mynt.bootcamp5.flag;

import lombok.Getter;

public enum VoucherEndpoint {
    VOUCHER_API("default", "voucherAPI_nadie");

    @Getter
    private String namespace;

    @Getter
    private String key;

    private VoucherEndpoint(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }
}

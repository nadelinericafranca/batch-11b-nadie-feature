package xyz.mynt.bootcamp5.remote;

public interface VoucherRemote {

    VoucherResponse getVoucher(String voucher) throws RuntimeException;

}

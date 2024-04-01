package xyz.mynt.bootcamp5.remote;

import lombok.Data;

@Data
public class VoucherResponse {

    private String code;

    private double discount;

    private String expiry;

    private Status status;

    private String version;

    private String error;

    public boolean isError() {
        return error != null;
    }

    public enum Status {
        AVAILABLE, CLAIMED
    }

}

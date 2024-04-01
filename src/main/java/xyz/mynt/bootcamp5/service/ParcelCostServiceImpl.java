package xyz.mynt.bootcamp5.service;

import xyz.mynt.bootcamp5.remote.VoucherRemote;
import xyz.mynt.bootcamp5.remote.VoucherResponse;
import xyz.mynt.bootcamp5.util.ParcelUtil;

public class ParcelCostServiceImpl implements ParcelCostService {

    private final VoucherRemote voucherRemote;

    public ParcelCostServiceImpl(VoucherRemote voucherRemote) {
        this.voucherRemote = voucherRemote;
    }

    @Override
    public double computeCost(double length, double width, double height, String voucher) throws RuntimeException {
        double cost = computeCost(length, width, height);

        VoucherResponse voucherResponse = voucherRemote.getVoucher(voucher);

        if (voucherResponse.isError()) {
            throw new RuntimeException(voucherResponse.getError());
        }

        if (voucherResponse.getStatus() == VoucherResponse.Status.CLAIMED) {
            throw new RuntimeException("Voucher already claimed.");
        }

        return cost - voucherResponse.getDiscount();
    }

    @Override
    public double computeCost(double length, double width, double height) throws RuntimeException {

        ParcelUtil.validateValues(length, width, height);

        double volume = ParcelUtil.computeVolume(length, width, height);

        double multiplier = ParcelUtil.getMultiplier(volume);

        return multiplier * volume;
    }


}

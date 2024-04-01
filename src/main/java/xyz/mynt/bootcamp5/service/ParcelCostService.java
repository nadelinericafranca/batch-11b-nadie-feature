package xyz.mynt.bootcamp5.service;

public interface ParcelCostService {

    double computeCost(double length, double width, double height) throws RuntimeException;
    double computeCost(double length, double width, double height, String voucher) throws RuntimeException;
}

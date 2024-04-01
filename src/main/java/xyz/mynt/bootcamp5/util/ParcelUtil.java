package xyz.mynt.bootcamp5.util;

public class ParcelUtil {

    public static double computeVolume(double length, double width, double height) {
        return length * width * height;
    }

    public static double getMultiplier(double volume) {
        // Small Parcel
        if (volume < 1500) {
            return 0.03;
        }
        // Medium Parcel
        if (volume < 2500) {
            return 0.04;
        }
        // Large Parcel
        return 0.05;
    }

    public static void validateValues(double length, double width, double height) throws RuntimeException {
        if (length <= 0) {
            throw new RuntimeException("Length must be greater than 0");
        }
        if (width <= 0) {
            throw new RuntimeException("Width must be greater than 0");
        }
        if (height <= 0) {
            throw new RuntimeException("Height must be greater than 0");
        }
    }

}

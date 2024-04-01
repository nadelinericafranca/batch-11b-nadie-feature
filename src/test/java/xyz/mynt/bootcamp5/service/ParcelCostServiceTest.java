package xyz.mynt.bootcamp5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelCostServiceTest {

    ParcelCostService parcelCostService = new ParcelCostServiceImpl(null);

    @Test
    @DisplayName("TC-10 Small parcel")
    public void testCase_TC10() {
        // Given
        double l = 10;
        double w = 10;
        double h = 10;

        // Execution
        double cost = parcelCostService.computeCost(l, w, h);

        // Assertion
        Assertions.assertEquals(30, cost);
    }

    @Test
    @DisplayName("TC-20 Medium parcel")
    public void testCase_TC20() {
        // Given
        double l = 20;
        double w = 10;
        double h = 10;

        // Execution
        double cost = parcelCostService.computeCost(l, w, h);

        // Assertion
        Assertions.assertEquals(80, cost);
    }

    @Test
    @DisplayName("TC-30 Large parcel")
    public void testCase_TC30() {
        // Given
        double l = 30;
        double w = 10;
        double h = 10;

        // Execution
        double cost = parcelCostService.computeCost(l, w, h);

        // Assertion
        Assertions.assertEquals(150, cost);
    }

    @Test
    @DisplayName("TC-40 Volume is exactly 1500")
    public void testCase_TC40() {
        // Given
        double l = 15;
        double w = 10;
        double h = 10;

        // Execution
        double cost = parcelCostService.computeCost(l, w, h);

        // Assertion
        Assertions.assertEquals(60, cost);
    }

    @Test
    @DisplayName("TC-50 Volume is exactly 1500")
    public void testCase_TC50() {
        // Given
        double l = 25;
        double w = 10;
        double h = 10;

        // Execution
        double cost = parcelCostService.computeCost(l, w, h);

        // Assertion
        Assertions.assertEquals(125, cost);
    }

    /*
    //       Negative Scenario
     */

    @Test
    @DisplayName("TC-60 Length is 0")
    public void testCase_TC60() {
        // Given
        double l = 0;
        double w = 10;
        double h = 10;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Length must be greater than 0"));
    }

    @Test
    @DisplayName("TC-70 Width is 0")
    public void testCase_TC70() {
        // Given
        double l = 10;
        double w = 0;
        double h = 10;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Width must be greater than 0"));
    }

    @Test
    @DisplayName("TC-80 Height is 0")
    public void testCase_TC80() {
        // Given
        double l = 10;
        double w = 10;
        double h = 0;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Height must be greater than 0"));
    }

    @Test
    @DisplayName("TC-90 Length is negative")
    public void testCase_TC90() {
        // Given
        double l = -1;
        double w = 10;
        double h = 10;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Length must be greater than 0"));
    }

    @Test
    @DisplayName("TC-100 Width is negative")
    public void testCase_TC100() {
        // Given
        double l = 10;
        double w = -1;
        double h = 10;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Width must be greater than 0"));
    }

    @Test
    @DisplayName("TC-110 Height is negative")
    public void testCase_TC110() {
        // Given
        double l = 10;
        double w = 10;
        double h = -1;

        // Execution
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> parcelCostService.computeCost(l, w, h),
                "Expected computeCost() to throw, but it didn't"
        );

        // Assertion
        assertTrue(thrown.getMessage().contains("Height must be greater than 0"));
    }
}

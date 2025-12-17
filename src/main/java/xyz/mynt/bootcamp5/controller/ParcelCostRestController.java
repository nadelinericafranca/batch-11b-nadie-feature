package xyz.mynt.bootcamp5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.mynt.bootcamp5.flag.MaintenanceEndpoint;
import xyz.mynt.bootcamp5.flag.MaintenanceFlag;
import xyz.mynt.bootcamp5.service.ParcelCostService;


@RestController
@RequestMapping("/parcel-cost-api")
public class ParcelCostRestController {

    private final ParcelCostService parcelCostService;

    private final MaintenanceFlag maintenanceFlag;

    public ParcelCostRestController(ParcelCostService parcelCostService, MaintenanceFlag maintenanceFlag) {
        this.parcelCostService = parcelCostService;
        this.maintenanceFlag = maintenanceFlag;
    }

    @GetMapping("/v1")
    public ResponseEntity<?> computeV1(
            @RequestParam(name = "v", required = false) String voucher,
            @RequestParam("l") double length,
            @RequestParam("w") double width,
            @RequestParam("h") double height) {

        // 3. Check if endpoint PARCEL_COST_API endpoint is in maintenance mode
        if (!maintenanceFlag.isUp(MaintenanceEndpoint.PARCEL_COST_API)) {
            // 3.1 Return 503
            return ResponseEntity.status(503).body(new ErrorMessage("System under maintenance"));
        }

        // 4. Continue as BAU
        try {
            double cost;

            // Without Voucher
            if (voucher == null) {
                cost = parcelCostService.computeCost(length, width, height);
                return ResponseEntity.ok(new Cost(cost));
            }

            // With voucher
            cost = parcelCostService.computeCost(length, width, height, voucher);
            return ResponseEntity.ok(new Cost(cost));
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().body(new ErrorMessage(re.getMessage()));
        }
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> exceptionMissingParam(MissingServletRequestParameterException e) {
        String missingParamMsg = switch (e.getParameterName()) {
            case "l" -> "Length is missing";
            case "w" -> "Width is missing";
            case "h" -> "Height is missing";
            default -> "";
        };

        return ResponseEntity.badRequest().body(new ErrorMessage(missingParamMsg));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> exceptionNonNumber(MethodArgumentTypeMismatchException e) {
        String invalidNumberParamMsg = switch (String.valueOf(e.getPropertyName())) {
            case "l" -> "Invalid value for Length";
            case "w" -> "Invalid value for Width";
            case "h" -> "Invalid value for Height";
            default -> "";
        };

        return ResponseEntity.badRequest().body(new ErrorMessage(invalidNumberParamMsg));
    }

    private record ErrorMessage(String error) {}

    private record Cost(double cost) {}

}

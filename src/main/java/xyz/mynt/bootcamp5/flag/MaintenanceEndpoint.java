package xyz.mynt.bootcamp5.flag;

import lombok.Getter;

public enum MaintenanceEndpoint {

    PARCEL_COST_API("default", "parcelCostAPI_nadie"); // Replace the <name>

    @Getter
    private String namespace;

    @Getter
    private String key;

    private MaintenanceEndpoint(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

}
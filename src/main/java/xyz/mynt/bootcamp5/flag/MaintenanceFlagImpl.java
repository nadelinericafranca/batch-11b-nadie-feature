package xyz.mynt.bootcamp5.flag;

import io.flipt.api.FliptClient;

public class MaintenanceFlagImpl implements MaintenanceFlag {

    private final FliptClient fliptClient;

    public MaintenanceFlagImpl(FliptClient fliptClient) {
        this.fliptClient = fliptClient;
    }

    @Override
    public boolean isUp(MaintenanceEndpoint endpoint) {
        // TODO implementation
        return false;
    }
}

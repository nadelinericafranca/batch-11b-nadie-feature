package xyz.mynt.bootcamp5.config;

import io.flipt.api.FliptClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import xyz.mynt.bootcamp5.flag.MaintenanceFlag;
import xyz.mynt.bootcamp5.flag.MaintenanceFlagImpl;
import xyz.mynt.bootcamp5.flag.VoucherFlag;
import xyz.mynt.bootcamp5.flag.VoucherFlagImpl;
import xyz.mynt.bootcamp5.remote.VoucherRemote;
import xyz.mynt.bootcamp5.remote.VoucherRemoteImpl;
import xyz.mynt.bootcamp5.service.ParcelCostService;
import xyz.mynt.bootcamp5.service.ParcelCostServiceImpl;

@Configuration
public class ServiceBeanConfig {

    @Bean
    public ParcelCostService parcelCostService(VoucherRemote voucherRemote) {
        return new ParcelCostServiceImpl(voucherRemote);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public VoucherRemote voucherRemote(RestTemplate restTemplate) {
        return new VoucherRemoteImpl(restTemplate);
    }

    @Bean
    public MaintenanceFlag maintenanceFlag(FliptClient fliptClient) {
        return new MaintenanceFlagImpl(fliptClient);
    }

    @Bean
    public VoucherFlag voucherFlag(FliptClient fliptClient) {
        return new VoucherFlagImpl(fliptClient);
    }

    @Bean
    public FliptClient fliptClient(FliptConfig config) {
        return FliptClient.builder()
                .url(config.getUrl())
                .build();
    }
}

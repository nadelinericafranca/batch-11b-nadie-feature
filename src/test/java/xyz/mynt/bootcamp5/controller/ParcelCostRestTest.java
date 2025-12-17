package xyz.mynt.bootcamp5.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.mynt.bootcamp5.Bootcamp5Application;
import xyz.mynt.bootcamp5.flag.MaintenanceFlag;
import xyz.mynt.bootcamp5.flag.VoucherFlag;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Bootcamp5Application.class)
@AutoConfigureMockMvc
public class ParcelCostRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MaintenanceFlag maintenanceFlag;

    @MockBean
    private VoucherFlag voucherFlag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TC-120 Length is missing")
    public void testCase_TC120() throws Exception {
        mvc.perform(
                get("/parcel-cost-api/v1")
                        .queryParam("w", "10")
                        .queryParam("h", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Length is missing")));

    }

    @Test
    @DisplayName("TC-130 Width is missing")
    public void testCase_TC130() throws Exception {
        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("l", "10")
                                .queryParam("h", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Width is missing")));

    }

    @Test
    @DisplayName("TC-140 Height is missing")
    public void testCase_TC140() throws Exception {
        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("l", "10")
                                .queryParam("w", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Height is missing")));

    }

    @Test
    @DisplayName("TC-150 Length is non number")
    public void testCase_TC150() throws Exception {
        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("l", "a")
                                .queryParam("w", "10")
                                .queryParam("h", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Invalid value for Length")));

    }

    @Test
    @DisplayName("TC-160 Width is non number")
    public void testCase_TC160() throws Exception {
        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("l", "10")
                                .queryParam("w", "a")
                                .queryParam("h", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Invalid value for Width")));

    }

    @Test
    @DisplayName("TC-170 Height is non number")
    public void testCase_TC170() throws Exception {
        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("l", "10")
                                .queryParam("h", "a")
                                .queryParam("w", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Invalid value for Height")));

    }

    @Test
    @DisplayName("TC-210 Maintenance On")
    public void testCase_TC210() throws Exception {
        // 3.1 Simulate the flag to turn maintenance ON by returning false on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false);

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(503))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("System under maintenance")));

    }

    @Test
    @DisplayName("TC-220 Maintenance Off")
    public void testCase_TC220() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true);

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(200))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cost", is(30.0)));

    }

    @Test
    @DisplayName("TC-230 V1 Maintenance Off, Voucher On")
    public void testCase_TC230() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true); // True = On/Voucher will be included ; False = Off/Voucher not used

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(200))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cost", is(30.0)));
    }

    @Test
    @DisplayName("TC-240 V1 Maintenance Off, Voucher Off")
    public void testCase_TC240() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false); // True = On/Voucher will be included ; False = Off/Voucher not used

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(200))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cost", is(30.0)));
    }

    @Test
    @DisplayName("TC-250 V1 Maintenance On, Voucher On")
    public void testCase_TC250() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true); // True = On/Voucher will be included

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(503))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("System under maintenance")));
    }

    @Test
    @DisplayName("TC-260 V1 Maintenance On, Voucher Off")
    public void testCase_TC260() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false); // True = On/Voucher will be included

        mvc.perform(
                        get("/parcel-cost-api/v1")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10"))
                .andExpect(status().is(503))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("System under maintenance")));
    }

    @Test
    @DisplayName("TC-270 V2 Maintenance Off, Voucher On")
    public void testCase_TC270() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true); // True = On/Voucher will be included ; False = Off/Voucher not used

        mvc.perform(
                        get("/parcel-cost-api/v2")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10")
                                .queryParam("v", "G10VOUCHER"))
                .andExpect(status().is(200))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cost", is(20.0)));
    }

    @Test
    @DisplayName("TC-280 V2 Maintenance Off, Voucher Off")
    public void testCase_TC280() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false); // True = On/Voucher will be included ; False = Off/Voucher not used

        mvc.perform(
                        get("/parcel-cost-api/v2")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10")
                                .queryParam("v", "G10VOUCHER"))
                .andExpect(status().is(400))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Voucher promotion is already expired")));
    }

    @Test
    @DisplayName("TC-290 V2 Maintenance On, Voucher On")
    public void testCase_TC290() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(true); // True = On/Voucher will be included

        mvc.perform(
                        get("/parcel-cost-api/v2")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10")
                                .queryParam("v", "G10VOUCHER"))
                .andExpect(status().is(503))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("System under maintenance")));
    }

    @Test
    @DisplayName("TC-300 V2 Maintenance On, Voucher Off")
    public void testCase_TC300() throws Exception {
        // 4.1 Simulate the flag to turn maintenance OFF by returning true on isUP
        Mockito.when(maintenanceFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false);

        Mockito.when(voucherFlag.isUp(ArgumentMatchers.any()))
                .thenReturn(false); // True = On/Voucher will be included

        mvc.perform(
                        get("/parcel-cost-api/v2")
                                .queryParam("w", "10")
                                .queryParam("h", "10")
                                .queryParam("l", "10")
                                .queryParam("v", "G10VOUCHER"))
                .andExpect(status().is(503))
                .andExpect(
                        content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("System under maintenance")));
    }
}

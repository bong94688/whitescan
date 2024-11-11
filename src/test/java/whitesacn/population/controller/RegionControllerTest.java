package whitesacn.population.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import whitesacn.population.dto.MonthlyStatsResponseDto;
import whitesacn.population.dto.PopulationStatsResponseDto;
import whitesacn.population.dto.RegionRequestDto;
import whitesacn.population.dto.RegionResponseDto;
import whitesacn.population.service.RegionService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.LocalTime;


@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("POST /api/regions - 지역 데이터 생성")
    @Test
    void createRegionTest() throws Exception {
        RegionRequestDto requestDto = new RegionRequestDto("Seoul", 10000000, LocalDate.now(), LocalTime.now());
        RegionResponseDto responseDto = new RegionResponseDto(1L, "Seoul", 10000000, LocalDate.now(), LocalTime.now());

        when(regionService.saveRegionData(any(RegionRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Seoul"))
                .andExpect(jsonPath("$.population").value(10000000));
    }

    @DisplayName("GET /api/regions/stats - 인구 통계 조회")
    @Test
    void getPopulationStatsTest() throws Exception {
        PopulationStatsResponseDto statsResponse = new PopulationStatsResponseDto(10000000, 2.0, 1.0);

        when(regionService.getPopulationStats("Seoul", LocalDate.of(2024, 11, 11))).thenReturn(statsResponse);

        mockMvc.perform(get("/api/regions/stats")
                        .param("name", "Seoul")
                        .param("date", "2024-11-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPopulation").value(10000000))
                .andExpect(jsonPath("$.oneHourChangeRate").value(2.0))
                .andExpect(jsonPath("$.threeHourChangeRate").value(1.0));
    }

    @DisplayName("GET /api/regions/monthly-stats - 월별 인구 통계 조회")
    @Test
    void getMonthlyStatsTest() throws Exception {
        MonthlyStatsResponseDto monthlyStatsResponse = new MonthlyStatsResponseDto(9900000.0, 2.5);

        when(regionService.getMonthlyStats("Seoul", 2024, 11)).thenReturn(monthlyStatsResponse);

        mockMvc.perform(get("/api/regions/monthly-stats")
                        .param("name", "Seoul")
                        .param("year", "2024")
                        .param("month", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averagePopulation").value(9900000.0))
                .andExpect(jsonPath("$.monthChangeRate").value(2.5));
    }
}
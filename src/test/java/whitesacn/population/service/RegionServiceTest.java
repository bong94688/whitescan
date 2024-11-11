package whitesacn.population.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import whitesacn.population.dto.MonthlyStatsResponseDto;
import whitesacn.population.dto.PopulationStatsResponseDto;
import whitesacn.population.dto.RegionRequestDto;
import whitesacn.population.entity.Region;
import whitesacn.population.repository.RegionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("saveRegionData - 지역 데이터 저장")
    @Test
    void saveRegionDataTest() {
        RegionRequestDto requestDto = new RegionRequestDto("Seoul", 10000000, LocalDate.now(), LocalTime.now());
        Region region = new Region("Seoul", 10000000, LocalDate.now(), LocalTime.now());

        when(regionRepository.save(any(Region.class))).thenReturn(region);

        var savedRegion = regionService.saveRegionData(requestDto);

        assertThat(savedRegion.getName()).isEqualTo("Seoul");
        assertThat(savedRegion.getPopulation()).isEqualTo(10000000);
    }

    @DisplayName("getPopulationStats - 인구 통계 조회")
    @Test
    void getPopulationStatsTest() {
        Region region = new Region("Seoul", 10000000, LocalDate.of(2024, 11, 11), LocalTime.of(10, 0));
        when(regionRepository.findByNameAndDate("Seoul", LocalDate.of(2024, 11, 11))).thenReturn(List.of(region));

        PopulationStatsResponseDto stats = regionService.getPopulationStats("Seoul", LocalDate.of(2024, 11, 11));

        assertThat(stats.getCurrentPopulation()).isEqualTo(10000000);
        assertThat(stats.getOneHourChangeRate()).isEqualTo(0);
    }

    @DisplayName("getMonthlyStats - 월별 인구 통계 조회")
    @Test
    void getMonthlyStatsTest() {
        Region region1 = new Region("Seoul", 9900000, LocalDate.of(2024, 11, 1), LocalTime.of(10, 0));
        Region region2 = new Region("Seoul", 10100000, LocalDate.of(2024, 11, 15), LocalTime.of(10, 0));
        when(regionRepository.findByNameAndDateBetween("Seoul", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 30)))
                .thenReturn(List.of(region1, region2));

        MonthlyStatsResponseDto stats = regionService.getMonthlyStats("Seoul", 2024, 11);

        assertThat(stats.getAveragePopulation()).isEqualTo(10000000.0);
    }
}

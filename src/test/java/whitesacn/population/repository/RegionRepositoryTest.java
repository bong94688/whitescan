package whitesacn.population.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import whitesacn.population.entity.Region;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @DisplayName("findByNameAndDate - 특정 이름과 날짜로 조회")
    @Test
    void findByNameAndDateTest() {
        // given
        LocalDate date = LocalDate.of(2024, 11, 11);
        Region region = new Region("Seoul", 10000000, date, LocalTime.of(10, 0));
        regionRepository.save(region);

        // when
        List<Region> regions = regionRepository.findByNameAndDate("Seoul", date);

        // then
        assertThat(regions).hasSize(1);
        assertThat(regions.get(0).getName()).isEqualTo("Seoul");
        assertThat(regions.get(0).getPopulation()).isEqualTo(10000000);
        assertThat(regions.get(0).getDate()).isEqualTo(date);
        assertThat(regions.get(0).getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("findByNameAndDateBetween - 특정 이름과 날짜 범위로 조회")
    @Test
    void findByNameAndDateBetweenTest() {
        // given
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);

        Region region1 = new Region("Seoul", 9900000, LocalDate.of(2024, 11, 5), LocalTime.of(10, 0));
        Region region2 = new Region("Seoul", 10100000, LocalDate.of(2024, 11, 25), LocalTime.of(10, 0));

        regionRepository.save(region1);
        regionRepository.save(region2);

        // when
        List<Region> regions = regionRepository.findByNameAndDateBetween("Seoul", startDate, endDate);

        // then
        assertThat(regions).hasSize(2);
        assertThat(regions).extracting("population").containsExactly(9900000, 10100000);
    }
}

package whitesacn.population.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whitesacn.population.dto.MonthlyStatsResponseDto;
import whitesacn.population.dto.PopulationStatsResponseDto;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer population;
	private LocalDate date;
	private LocalTime time;

	public Region(String name, Integer population, LocalDate date, LocalTime time) {
		this.name = name;
		this.population = population;
		this.date = date;
		this.time = time;
	}

	// 인구 증감률 계산
	public double calculateChangeRate(Integer previousPopulation) {
		return previousPopulation == 0 ? 0 : ((double) (population - previousPopulation) / previousPopulation) * 100;
	}

	// PopulationStatsResponseDto 변환 메서드
	public PopulationStatsResponseDto toPopulationStatsResponseDto(int oneHourBefore, int threeHoursBefore) {
		return new PopulationStatsResponseDto(
			population,
			calculateChangeRate(oneHourBefore),
			calculateChangeRate(threeHoursBefore)
		);
	}

	// MonthlyStatsResponseDto 변환 메서드
	public static MonthlyStatsResponseDto toMonthlyStatsResponseDto(double averagePopulation, double changeRate) {
		return new MonthlyStatsResponseDto(averagePopulation, changeRate);
	}
}

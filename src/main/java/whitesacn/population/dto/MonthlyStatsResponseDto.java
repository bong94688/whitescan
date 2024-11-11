package whitesacn.population.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyStatsResponseDto {
	private Double averagePopulation;
	private Double monthChangeRate;
}

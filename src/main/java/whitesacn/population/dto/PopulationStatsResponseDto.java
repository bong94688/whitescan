package whitesacn.population.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PopulationStatsResponseDto {
	private Integer currentPopulation;
	private Double oneHourChangeRate;
	private Double threeHourChangeRate;
}

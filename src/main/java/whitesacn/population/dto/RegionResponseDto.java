package whitesacn.population.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class RegionResponseDto {
	private Long id;
	private String name;
	private Integer population;
	private LocalDate date;
	private LocalTime time;
}

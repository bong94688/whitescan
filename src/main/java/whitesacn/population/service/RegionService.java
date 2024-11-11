package whitesacn.population.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import whitesacn.population.entity.Region;
import whitesacn.population.dto.MonthlyStatsResponseDto;
import whitesacn.population.dto.PopulationStatsResponseDto;
import whitesacn.population.dto.RegionRequestDto;
import whitesacn.population.dto.RegionResponseDto;
import whitesacn.population.repository.RegionRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegionService {

	private final RegionRepository regionRepository;

	public RegionResponseDto saveRegionData(RegionRequestDto request) {
		Region region = new Region(request.getName(), request.getPopulation(), request.getDate(), request.getTime());
		Region savedRegion = regionRepository.save(region);
		return new RegionResponseDto(savedRegion.getId(), savedRegion.getName(), savedRegion.getPopulation(), savedRegion.getDate(), savedRegion.getTime());
	}

	public PopulationStatsResponseDto getPopulationStats(String name, LocalDate date) {
		List<Region> regions = regionRepository.findByNameAndDate(name, date);
		if (regions.isEmpty()) return null;

		Region latestRegion = regions.get(regions.size() - 1);
		int oneHourBefore = getPopulationBeforeTime(name, date, 1);
		int threeHoursBefore = getPopulationBeforeTime(name, date, 3);

		return latestRegion.toPopulationStatsResponseDto(oneHourBefore, threeHoursBefore);
	}

	public MonthlyStatsResponseDto getMonthlyStats(String name, int year, int month) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		List<Region> regions = regionRepository.findByNameAndDateBetween(name, startDate, endDate);

		if (regions.isEmpty()) return null;

		double averagePopulation = regions.stream().mapToInt(Region::getPopulation).average().orElse(0);
		double lastMonthAverage = calculateLastMonthAverage(name, startDate);
		double changeRate = lastMonthAverage == 0 ? 0 : ((averagePopulation - lastMonthAverage) / lastMonthAverage) * 100;

		return Region.toMonthlyStatsResponseDto(averagePopulation, changeRate);
	}

	private int getPopulationBeforeTime(String name, LocalDate date, int hoursBefore) {
		LocalTime targetTime = LocalTime.now().minusHours(hoursBefore);
		return regionRepository.findByNameAndDate(name, date).stream()
			.filter(r -> r.getTime().isBefore(targetTime))
			.map(Region::getPopulation)
			.findFirst()
			.orElse(0);
	}

	private double calculateLastMonthAverage(String name, LocalDate startDate) {
		LocalDate lastMonthStart = startDate.minusMonths(1);
		LocalDate lastMonthEnd = lastMonthStart.plusMonths(1).minusDays(1);
		List<Region> lastMonthRegions = regionRepository.findByNameAndDateBetween(name, lastMonthStart, lastMonthEnd);
		return lastMonthRegions.stream().mapToInt(Region::getPopulation).average().orElse(0);
	}
}

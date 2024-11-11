package whitesacn.population.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import whitesacn.population.dto.MonthlyStatsResponseDto;
import whitesacn.population.dto.PopulationStatsResponseDto;
import whitesacn.population.dto.RegionRequestDto;
import whitesacn.population.dto.RegionResponseDto;
import whitesacn.population.service.RegionService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/regions")
public class RegionController {

	private final RegionService regionService;

	@PostMapping
	public RegionResponseDto createRegion(@RequestBody RegionRequestDto request) {
		return regionService.saveRegionData(request);
	}

	@GetMapping("/stats")
	public PopulationStatsResponseDto getPopulationStats(@RequestParam String name, @RequestParam LocalDate date) {
		return regionService.getPopulationStats(name, date);
	}

	@GetMapping("/monthly-stats")
	public MonthlyStatsResponseDto getMonthlyStats(@RequestParam String name, @RequestParam int year, @RequestParam int month) {
		return regionService.getMonthlyStats(name, year, month);
	}
}

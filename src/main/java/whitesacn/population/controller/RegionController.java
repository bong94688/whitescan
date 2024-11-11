package whitesacn.population.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

//NOTE: http://localhost:8080/swagger-ui/index.html
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    //NOTE: {
    //  "name": "Seoul",
    //  "population": 10000000,
    //  "date": "2024-11-11",
    //  "time": "10:00:00"
    //}
    @Operation(summary = "지역 인구 데이터 생성", description = "지역 이름, 인구수, 날짜, 시간 정보를 입력받아 인구 데이터를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "데이터 생성 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public RegionResponseDto createRegion(@RequestBody RegionRequestDto request) {
        return regionService.saveRegionData(request);
    }


    @Operation(summary = "인구 통계 조회", description = "특정 지역과 날짜에 대한 인구 통계를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PopulationStatsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "데이터 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    // NOTE: http://localhost:8080/api/regions/stats?name=Seoul&date=2024-11-11
    @GetMapping("/stats")
    public PopulationStatsResponseDto getPopulationStats(@Parameter(description = "조회할 지역 이름") @RequestParam String name,
                                                         @Parameter(description = "조회할 날짜") @RequestParam LocalDate date) {
        return regionService.getPopulationStats(name, date);
    }

    @Operation(summary = "월별 인구 통계 조회", description = "특정 지역의 특정 연도와 월에 대한 월별 인구 통계를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MonthlyStatsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "데이터 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    // NOTE: http://localhost:8080/api/regions/monthly-stats?name=Seoul&year=2024&month=11
    @GetMapping("/monthly-stats")
    public MonthlyStatsResponseDto getMonthlyStats(@Parameter(description = "조회할 지역 이름") @RequestParam String name,
                                                   @Parameter(description = "조회할 연도") @RequestParam int year,
                                                   @Parameter(description = "조회할 월") @RequestParam int month) {
        return regionService.getMonthlyStats(name, year, month);
    }
}

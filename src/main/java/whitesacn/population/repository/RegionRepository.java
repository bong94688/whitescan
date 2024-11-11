package whitesacn.population.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whitesacn.population.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findByNameAndDate(String name, LocalDate date);
	List<Region> findByNameAndDateBetween(String name, LocalDate startDate, LocalDate endDate);
}
package app.repository;

import app.entity.Sensorvalue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorvalueRepository extends JpaRepository<Sensorvalue, Integer> {
}
package app.repository;

import app.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor getSensorByAddress(String unknownAttr1);

    Sensor getReferenceByAddress(String unknownAttr1);
}
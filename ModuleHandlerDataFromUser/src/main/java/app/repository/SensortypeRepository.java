package app.repository;

import app.entity.Sensortype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensortypeRepository extends JpaRepository<Sensortype, Integer> {
}
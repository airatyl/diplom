package app.repository;

import app.entity.Sensorvalue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface SensorvalueRepository extends JpaRepository<Sensorvalue, Integer> {
    public List<Sensorvalue> findAllByRecordingtimeBetween(Instant recordingtime, Instant recordingtime2);
}
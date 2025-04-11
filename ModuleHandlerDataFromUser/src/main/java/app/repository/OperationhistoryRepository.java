package app.repository;

import app.entity.Operationhistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface OperationhistoryRepository extends JpaRepository<Operationhistory, Integer> {
    List<Operationhistory> findAllByOperationDateBetween(Instant operationDate, Instant operationDate2);
}
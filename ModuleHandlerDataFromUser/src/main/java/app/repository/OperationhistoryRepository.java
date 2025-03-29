package app.repository;

import app.entity.Operationhistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationhistoryRepository extends JpaRepository<Operationhistory, Integer> {
}
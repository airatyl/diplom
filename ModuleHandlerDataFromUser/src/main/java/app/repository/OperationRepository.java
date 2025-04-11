package app.repository;

import app.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    Operation getOperationByName(String name);

    Operation getReferenceByName(String name);
}
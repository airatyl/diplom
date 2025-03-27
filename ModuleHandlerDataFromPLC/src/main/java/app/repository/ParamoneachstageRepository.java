package app.repository;

import app.entity.Moldingstage;
import app.entity.Paramoneachstage;
import app.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamoneachstageRepository extends JpaRepository<Paramoneachstage, Integer> {

    Paramoneachstage getParamoneachstageBySensor_IdAndMoldingstage_Id(Integer sensor_id, Integer moldingstage_id);
}
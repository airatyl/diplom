package app.repository;

import app.entity.Paramoneachstage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamoneachstageRepository extends JpaRepository<Paramoneachstage, Integer> {

    Paramoneachstage getParamoneachstageBySensor_IdAndMoldingstage_Id(Integer sensor_id, Integer moldingstage_id);
    Paramoneachstage getParamoneachstageByMoldingstage_IdAndControlparam(Integer moldingstage_id, String controlparam);

}
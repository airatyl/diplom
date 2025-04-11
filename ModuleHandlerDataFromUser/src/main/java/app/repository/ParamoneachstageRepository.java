package app.repository;

import app.entity.Paramoneachstage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamoneachstageRepository extends JpaRepository<Paramoneachstage, Integer> {

    Paramoneachstage getParamoneachstageBySensor_IdAndMoldingstage_Id(Integer sensor_id, Integer moldingstage_id);
    Paramoneachstage getParamoneachstageByMoldingstage_IdAndId_Controlparam(Integer moldingstage_id, String controlparam);
    Paramoneachstage getFirstBySensor_AddressAndMoldingstage_Id(String sensor_address, Integer moldingstage_id);

    Paramoneachstage getParamoneachstageBySensor_AddressAndMoldingstage_Id(Object unknownAttr1, Integer id);
}
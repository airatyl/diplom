package app.repository;

import app.entity.Paramoneachstage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamoneachstageRepository extends JpaRepository<Paramoneachstage, Integer> {

    Paramoneachstage getParamoneachstageByMoldingstage_IdAndId_Controlparam(Integer moldingstage_id, String controlparam);

    Paramoneachstage getParamoneachstageBySensor_AddressAndMoldingstage_Id(String unknownAttr1, Integer id);
}
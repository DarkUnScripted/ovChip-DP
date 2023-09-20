package nl.hu.dp.data;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {

    public boolean save(OVChipkaart ovChipkaart);

    public boolean update(OVChipkaart ovChipkaart);

    public boolean delete(OVChipkaart ovChipkaart);

    public OVChipkaart findById(int id);

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);

    public List<OVChipkaart> findAll();

}

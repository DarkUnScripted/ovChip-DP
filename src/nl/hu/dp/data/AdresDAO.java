package nl.hu.dp.data;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;

import java.util.List;

public interface AdresDAO {

    public boolean save(Adres Adres);

    public boolean update(Adres Adres);

    public boolean delete(Adres Adres);

    public Adres findById(int id);

    public Adres findByReiziger(Reiziger reiziger);

    public List<Adres> findAll();

}

package nl.hu.dp.data;

import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.util.List;

public interface ProductDAO {

    public boolean save(Product product);

    public boolean update(Product product);

    public boolean delete(Product product);

    public Product findById(int id);

    public List<Product> findByOvChipkaart(int id);

    public List<Product> findAll();

}

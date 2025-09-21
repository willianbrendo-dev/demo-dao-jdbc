package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	public void insert(Seller obj);
	public void update(Seller obj);
	public void deleteById(Seller obj);
	public Department findId(Seller id);
	public List<Seller> findAll();
}

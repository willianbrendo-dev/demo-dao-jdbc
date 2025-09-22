package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	public void insert(Seller obj);
	public void update(Seller obj);
	public void deleteById(Integer id);
	public Seller findById(Integer id);
	List<Seller> findByDepartment(Department department);
	public List<Seller> findAll();
}

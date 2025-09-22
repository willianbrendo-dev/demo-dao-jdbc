package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		try (PreparedStatement st = conn.prepareStatement(
				"INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+"VALUES "
				+ "(?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS
				)) {
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						int id = rs.getInt(1);
						obj.setId(id);
						System.out.println("Inserção bem-sucedida! Novo ID: " + id);
					}
				}
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada.");
			}
		}
		catch (SQLException e) {
			throw new DbException("Erro ao inserir vendedor: " + e.getMessage());
		}

	}

	@Override
	public void update(Seller obj) {

		try (PreparedStatement st = conn.prepareStatement(
				"UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ?"
				)) {
			
			
			st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado! ID não existe ou não foi atualizado.");
            }
		}
		catch (SQLException e) {
			throw new DbException("Erro ao atualizar vendedor: " + e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		
		try (PreparedStatement st = conn.prepareStatement(
					"DELETE FROM seller WHERE Id = ?"
				)) {
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("ID não existe!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}
	
	
	@Override
    public Seller findById(Integer id) {
        
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE seller.Id = ?")
             ) {

            st.setInt(1, id); // Define o valor do ID no placeholder.
            
            try (ResultSet rs = st.executeQuery()) {
                
                if (rs.next()) {
                    // Instancia um Departamento e um Vendedor com os dados do ResultSet.
                    Department dep = instantiateDepartment(rs);
                    Seller obj = instantiateSeller(rs, dep);
                    return obj;
                }
            }
            // Se o ResultSet estiver vazio, retorna null.
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
	
	public List<Seller> findByDepartment(Department department) {
		try (PreparedStatement st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE DepartmentId = ? "
                + "ORDER BY Name"
				)
			) {
			st.setInt(1, department.getId());
			try (ResultSet rs = st.executeQuery()) {
				List<Seller> list = new ArrayList<>();
				Map<Integer, Department> map = new HashMap<>();
				
				while (rs.next()) {
					Department dep = map.get(rs.getInt("DepartmentId"));
					if (dep == null) {
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);
					list.add(obj);
				}
				return list;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	
	
	public Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBirthDate(rs.getDate("BirthDate")); 
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDepartment(dep);
		return obj;
	}
	
	public Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	


	@Override
	public List<Seller> findAll() {
		try (PreparedStatement st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "ORDER BY Name");
				) {
			
			try (ResultSet rs = st.executeQuery()) {
				List<Seller> list = new ArrayList<>();
				Map<Integer, Department> map = new HashMap<>();
				
				while (rs.next()) {
					Department dep = map.get(rs.getInt("DepartmentId"));
					if (dep == null) {
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);
					list.add(obj);
				}
				return list;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

}

package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import db.DbIntegrityExeption;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		try (PreparedStatement st = conn.prepareStatement(
				"INSERT INTO department "
				+ "(Name) "
				+ "VALUES "
				+ "(?)", Statement.RETURN_GENERATED_KEYS
				)) {
			st.setString(1, obj.getName());
			
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
			throw new DbException("Erro ao inserir departamento: " + e.getMessage());
		}

	}

	@Override
	public void update(Department obj) {
		
		try (PreparedStatement st = conn.prepareStatement(
				"UPDATE department "
                + "SET Name = ? "
                + "WHERE Id = ?"
				)) {
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
                throw new DbException("ID não existe ou não foi atualizado.");
            }
		}
		catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

	}

	@Override
	public void deleteById(Integer id) {
		
		try (PreparedStatement st = conn.prepareStatement(
				"DELETE FROM department WHERE Id = ?")
	            ) {
			 st.setInt(1, id);
			 int rowsAffected = st.executeUpdate();
			 
			 if (rowsAffected == 0) {
	                throw new DbException("ID não existe!");
	            }
		}
		catch (SQLException e) {
			throw new DbIntegrityExeption(e.getMessage());
		}

	}

	@Override
	public Department findById(Integer id) {
		
		try (PreparedStatement st = conn.prepareStatement(
				"SELECT * FROM department WHERE Id = ?");
	             ) {
			st.setInt(1, id);
			
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Department obj = new Department();
                    obj.setId(rs.getInt("Id"));
                    obj.setName(rs.getString("Name"));
                    return obj;
				}
			}
			return null;
		}
		catch (Exception e) {
			throw new DbException(e.getMessage());
		}
	}
	

	@Override
	public List<Department> findAll() {

		try (PreparedStatement st = conn.prepareStatement(
				"SELECT * FROM department ORDER BY Name");
	             ) {
			try (ResultSet rs = st.executeQuery()) {
				List<Department> list = new ArrayList<>();
				
				while (rs.next()) {
					Department dep = new Department();
					dep.setId(rs.getInt("Id"));
					dep.setName(rs.getString("Name"));
					list.add(dep);
				}
				return list;
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
				
	}

}

package app;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Main2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TESTE 1: findById ===");
        // Testa o método findById, buscando um departamento que você sabe que existe (ex: ID 1).
        Department department = departmentDao.findById(1);
        System.out.println(department);
        
        System.out.println("\n=== TESTE 2: insert ===");
        // Testa a inserção de um novo departamento.
        // Crie um novo objeto Department, sem ID, pois será gerado pelo banco.
        Department newDepartment = new Department(null, "Food");
        departmentDao.insert(newDepartment);
        System.out.println("Novo ID do departamento: " + newDepartment.getId());
        
        System.out.println("\n=== TESTE 3: update ===");
        // Testa a atualização de um departamento existente.
        // Primeiro, busca o departamento que acabamos de criar.
        department = departmentDao.findById(newDepartment.getId());
        // Altera o nome do departamento.
        department.setName("Eletronics");
        departmentDao.update(department);
        System.out.println("Atualização bem-sucedida!");
        
        System.out.println("\n=== TESTE 4: delete ===");
        // Testa a exclusão do departamento que acabamos de criar.
        departmentDao.deleteById(newDepartment.getId());
        System.out.println("Deleção bem-sucedida!");

        System.out.println("\n=== TESTE 5: findAll ===");
        // Testa o método findAll, que deve retornar todos os departamentos.
        List<Department> list = departmentDao.findAll();
        for (Department dep : list) {
            System.out.println(dep);
        }

	}

}

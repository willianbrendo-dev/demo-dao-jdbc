package app;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		// 1. Instancia o DAO usando a fábrica.
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("=== TESTE 1: findById ===");
        // Testa o método findById, buscando um vendedor que você sabe que existe (ex: ID 3).
        Seller seller = sellerDao.findById(3); 
        System.out.println(seller);
        
        System.out.println("\n=== TESTE 2: findByDepartment ===");
        // Testa o método findByDepartment.
        // Crie um objeto de departamento com o ID que você quer buscar.
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        // Itera sobre a lista para imprimir todos os vendedores do departamento.
        for (Seller obj : list) {
            System.out.println(obj);
        }
        
        System.out.println("\n=== TESTE 3: findAll ===");
        // Testa o método findAll.
        // Ele vai retornar todos os vendedores de todos os departamentos.
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }
        
        System.out.println("\n=== TESTE 4: insert ===");
        // Testa a inserção de um novo vendedor.
        // Crie um objeto Department com o ID do departamento onde o vendedor será inserido.
        Department dep = new Department(1, null);
        Seller newSeller = new Seller(null, "Greg Brown", "greg@gmail.com", new Date(), 4000.0, dep);
        sellerDao.insert(newSeller);
        // O método de inserção retorna o ID gerado, então você pode acessá-lo aqui.
        System.out.println("Novo ID do vendedor: " + newSeller.getId());
        
        System.out.println("\n=== TESTE 5: update ===");
        // Testa a atualização de um vendedor existente.
        // Primeiro, busca um vendedor existente (ex: o que acabamos de inserir).
        seller = sellerDao.findById(newSeller.getId());
        // Altera o nome e o salário base do vendedor.
        seller.setName("Greg Williams");
        seller.setBaseSalary(4200.0);
        sellerDao.update(seller);
        System.out.println("Atualização bem-sucedida!");
        
        System.out.println("\n=== TESTE 6: delete ===");
        // Testa a exclusão de um vendedor.
        // Use o ID do vendedor que você quer apagar.
        System.out.println("Digite o ID para deletar: ");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		sc.close();
        
        // Por praticidade, vamos deletar o que acabamos de criar.
        sellerDao.deleteById(newSeller.getId());
        System.out.println("Deleção bem-sucedida!");
				
	}

}

package app;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		// A fábrica cria o objeto DAO e já injeta a conexão.
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        sellerDao.deleteById(9);		
				
	}

}

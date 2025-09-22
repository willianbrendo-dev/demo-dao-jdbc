package app;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		// A fábrica cria o objeto DAO e já injeta a conexão.
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDao.findById(3); 
        System.out.println(seller);
        
        // A conexão é fechada quando o programa termina.

				
				
	}

}

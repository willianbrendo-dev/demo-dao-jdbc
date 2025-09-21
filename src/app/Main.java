package app;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		
		
		
		Seller seller = new Seller(1, "Willian Brendo", "willianbrendo.dev@gmail.com", new Date(), 2800.00, obj);
		
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(seller);

				
				
	}

}

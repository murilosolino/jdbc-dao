package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("===== TEST 1 FindByID ======");
        Seller seller = sellerDao.findById(5);
        System.out.println(seller);

        System.out.println();

        System.out.println("===== TEST 2 FindByID ======");
        Department dep = new Department(4,null);
        List<Seller> list = sellerDao.findByDepartment(dep);

        for (Seller s : list){
            System.out.println(s);
        }

        System.out.println("===== TEST 3 FindAll ======");
        List<Seller> list2 = sellerDao.findAll();

        for (Seller s : list2){
            System.out.println(s);
        }

        System.out.println("===== TEST 4 insert ======");
        Seller newSeller = new Seller(null, "Steve", "steve@gmail.com", new Date(), 3000.0, dep);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("===== TEST 5 insert ======");
        seller = sellerDao.findById(1);
        seller.setName("Bruce Wayne");
        sellerDao.update(seller);
        System.out.println("Update Complete");

        DB.closeConnection();
    }
}
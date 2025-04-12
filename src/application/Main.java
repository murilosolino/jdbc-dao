package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

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


        DB.closeConnection();
    }
}
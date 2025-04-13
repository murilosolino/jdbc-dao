package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /***************** SELLER TEST ************************/
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

        System.out.println("===== TEST 5 update ======");
        seller = sellerDao.findById(1);
        seller.setName("Bruce Wayne");
        sellerDao.update(seller);
        System.out.println("Update Complete");

        System.out.println("===== TEST 6 delete ======");
        sellerDao.deleteById(10);

        /**********************DEPARTMENT TEST*********************/
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        Department dep2 = new Department(null, "DevOps");

        System.out.println("======= TEST 5 Department insert ==============");
        departmentDao.insert(dep2);
        System.out.println();

        System.out.println("===================");
        System.out.println(dep2.toString());
        System.out.println();

        System.out.println("========= TEST 6 Department update =============");
        dep2.setId(8);
        dep2.setName("Financial");
        departmentDao.update(dep2);
        System.out.println();

        System.out.println("============ TEST 7 Department delete ===============");
        departmentDao.deleteById(10);
        System.out.println();

        System.out.println("========= TEST 8 Department findById =============");
        Department department = departmentDao.findById(3);
        System.out.println(department.toString());
        System.out.println();

        System.out.println("========= TEST 9 Department findAll =============");
        List<Department> departmentList = departmentDao.findAll();
        for (Department dep1 : departmentList){
            System.out.println(dep1);
        }
        DB.closeConnection();

    }
}
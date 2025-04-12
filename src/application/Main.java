package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // teste de instanciacao
        Department obj = new Department(1,"it");

        Seller seller = new Seller(1, "bob", "bob@gmail.com", new Date(), 3000.0, obj);
        System.out.println(seller);
    }
}
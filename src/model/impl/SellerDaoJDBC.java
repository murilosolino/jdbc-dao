package model.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id= ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()){
                    Integer idSeller = rs.getInt("Id");
                    String name = rs.getString("Name");
                    String email = rs.getString("Email");
                    Date birthDate = rs.getDate("BirthDate");
                    Double baseSalary = rs.getDouble("BaseSalary");

                    Integer departmentId = rs.getInt("DepartmentId");
                    String depName = rs.getString("DepName");
                    Department department = new Department(departmentId, depName);

                    Seller seller = new Seller(idSeller, name, email, birthDate, baseSalary, department);
                    return seller;
            }

            return null;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}

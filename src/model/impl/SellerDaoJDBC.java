package model.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement("INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);


            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected >0){
               rs = st.getGeneratedKeys();
                if (rs.next()){
                    int Id = rs.getInt(1);
                    obj.setId(Id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro insperado: Nenhuma linha afetada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "UPDATE seller " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "WHERE Id = ?"
            );


            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            int rowsAffected = st.executeUpdate();
            System.out.println("ROWS AFFECTED: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            System.out.println("Conexão estabelecida: " + (conn != null)); // Log da conexão
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id= ?"
            );

            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()){
                    Department department = instanciateDepartment(rs);
                    Seller seller = instanciateSeller(rs,department);
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DepName " +
                    "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                    "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            HashMap<Integer, Department> map = new HashMap<>();
            while (rs.next()){

               Department dep = map.get(rs.getInt("DepartmentId"));

               if (dep == null){
                   dep = instanciateDepartment(rs);
                   map.put(rs.getInt("DepartmentId"), dep);
               }


               Seller seller = instanciateSeller(rs, dep);
               list.add(seller);

            }
            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            System.out.println("Conexão estabelecida: " + (conn != null)); // Log da conexão
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId= ? ORDER BY Name"
            );

            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller> list =  new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instanciateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instanciateSeller(rs,dep);
                list.add(seller);
            }
            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department obj = new Department();
        obj.setId(rs.getInt("DepartmentId"));
        obj.setName(rs.getString("DepName"));
        return obj;
    }

    private Seller instanciateSeller(ResultSet rs, Department department) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(department);
        return obj;
    }
}

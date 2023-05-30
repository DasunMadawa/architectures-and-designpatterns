package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.CustomerDAO;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOimpl implements CustomerDAO {

    @Override
    public List<CustomerDTO> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");

        List<CustomerDTO> customerDTOList = new ArrayList<>();
        while (rst.next()) {
            customerDTOList.add(new CustomerDTO(rst.getString("id"), rst.getString("name"), rst.getString("address")));
        }

        return customerDTOList;

    }

    @Override
    public boolean add(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Customer (id,name, address) VALUES (?,?,?)", customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress());

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM Customer WHERE id=?", id);
        return rs.next();

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Customer WHERE id=?", id);

    }

    @Override
    public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?", customerDTO.getName(), customerDTO.getAddress(), customerDTO.getId());

    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }

    }

    @Override
    public CustomerDTO search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM Customer WHERE id=?", newValue);
        if (rs.next()) {
            return new CustomerDTO(newValue + "", rs.getString("name"), rs.getString("address"));
        }

        return null;

    }


}

package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOimpl implements ItemDAO {
    @Override
    public List<ItemDTO> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item");

        List<ItemDTO> itemDTOList = new ArrayList<>();

        while (rst.next()) {
            itemDTOList.add(new ItemDTO(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
        }

        return itemDTOList;

    }

    @Override
    public boolean add(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)" , itemDTO.getCode() , itemDTO.getDescription() , itemDTO.getUnitPrice() , itemDTO.getQtyOnHand() );

    }

    @Override
    public boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?" , itemDTO.getDescription() , itemDTO.getUnitPrice() , itemDTO.getQtyOnHand() , itemDTO.getCode() );

    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT code FROM Item WHERE code=?" , code);
        return rs.next();

    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM item WHERE CODE = ? " , code);

    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }

    }

    @Override
    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE code=?", code);
        if (rst.next()) {
            return new ItemDTO(code + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        }
        return null;

    }
}
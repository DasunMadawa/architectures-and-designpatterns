package bo.custom;

import model.CustomerDTO;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public List<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException;
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existItem(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    public String generateNewId() throws SQLException, ClassNotFoundException;
    public ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException;

}

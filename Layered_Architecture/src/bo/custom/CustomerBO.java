package bo.custom;

import model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public List<CustomerDTO> loadAllCustomers() throws SQLException, ClassNotFoundException;
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public String generateNewId() throws SQLException, ClassNotFoundException;
    public CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException;

}

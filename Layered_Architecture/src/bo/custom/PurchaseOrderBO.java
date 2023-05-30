package bo.custom;

import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDetailDTO;
import model.PlaceOrderDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException ;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    public String generateOrderID() throws SQLException, ClassNotFoundException ;

    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean purchaseOrder(PlaceOrderDTO placeOrderDTO);

    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;

}

package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import model.OrderDTO;

public interface OrderDAO extends CrudDAO<OrderDTO , String> , SuperDAO {

}

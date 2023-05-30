package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import model.OrderDetailDTO;

public interface OrderDetailsDAO extends CrudDAO<OrderDetailDTO , String> , SuperDAO {

}

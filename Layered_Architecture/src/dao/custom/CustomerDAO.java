package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import model.CustomerDTO;

public interface CustomerDAO extends CrudDAO<CustomerDTO , String> , SuperDAO {
    // unique methods
}

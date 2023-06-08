
package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public List<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> itemList = new ArrayList<>();

        for (Item item : itemDAO.loadAll() ) {
            itemList.add(new ItemDTO( item.getCode() , item.getDescription() , item.getUnitPrice() , item.getQtyOnHand() ));
        }

        return itemList;
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getCode() , dto.getDescription() , dto.getQtyOnHand() , dto.getUnitPrice() ));
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getCode() , dto.getDescription() , dto.getQtyOnHand() , dto.getUnitPrice() ));
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewId();
    }

    @Override
    public ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(newValue);
        return new ItemDTO(item.getCode() , item.getDescription() , item.getUnitPrice() , item.getQtyOnHand() );
    }





}

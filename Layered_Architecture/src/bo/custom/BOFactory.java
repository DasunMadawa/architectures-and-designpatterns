package bo.custom;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;

import java.sql.SQLException;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public enum BOTypes {
        CUSTOMER , ITEM , PURCHASE_ORDER

    }

    public static BOFactory getBOFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;

    }

    public SuperBO getBO(BOTypes boTypes) throws SQLException, ClassNotFoundException {
        switch (boTypes){
            case CUSTOMER :
                return new CustomerBOImpl();
            case ITEM :
                return new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            default :
                return null;

        }


    }



}

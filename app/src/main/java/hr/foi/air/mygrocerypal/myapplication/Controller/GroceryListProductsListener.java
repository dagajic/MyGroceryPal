package hr.foi.air.mygrocerypal.myapplication.Controller;

import java.util.ArrayList;

import hr.foi.air.mygrocerypal.myapplication.Model.GroceryListProductsModel;

public interface GroceryListProductsListener {
    public void groceryListProductsReceived(ArrayList<GroceryListProductsModel> groceryListProducts);
}

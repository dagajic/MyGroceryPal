package hr.foi.air.mygrocerypal.myapplication.FirebaseHelper.Listeners;

import hr.foi.air.mygrocerypal.myapplication.Core.GroceryListOperation;

public interface GroceryListStatusListener {
    public void groceryListStatusReceived(String groceryListID, String groceryListStatus, GroceryListOperation operation);
}
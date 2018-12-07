package hr.foi.air.mygrocerypal.myapplication.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hr.foi.air.mygrocerypal.myapplication.Controller.Adapters.SelectProductsAdapter;
import hr.foi.air.mygrocerypal.myapplication.Controller.Listeners.SelectProductsListener;
import hr.foi.air.mygrocerypal.myapplication.Controller.SelectProductsController;
import hr.foi.air.mygrocerypal.myapplication.Model.GroceryListProductsModel;
import hr.foi.air.mygrocerypal.myapplication.Model.GroceryListsModel;
import hr.foi.air.mygrocerypal.myapplication.Model.ProductsModel;
import hr.foi.air.mygrocerypal.myapplication.R;

import static android.app.Activity.RESULT_OK;

public class SelectProductsFragment extends Fragment implements SelectProductsListener{
    private Button addProductsToGroceryList;
    private SelectProductsController selectProductsController;
    private RecyclerView recyclerView;
    private SelectProductsAdapter selectProductsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_products, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectProductsController = new SelectProductsController(this);

        recyclerView = view.findViewById(R.id.recycler_view);
        addProductsToGroceryList = view.findViewById(R.id.addProductsToGroceryList);

        selectProductsController.loadGroceryLists(getArguments().getString("store_name"));

        addProductsToGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroceryListProductsModel> listOfProducts = selectProductsAdapter.getListOfProducts();

                //ukoliko ne postoji prethodni fragment ne čini ništa!
                if(getFragmentManager().getBackStackEntryCount() > 0){
                    Intent intent = new Intent(getContext(), SelectProductsFragment.class);
                    intent.putExtra("groceryListOfProducts", (Serializable) listOfProducts);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                    getFragmentManager().popBackStack();
                    Toast.makeText(getContext(), "Dodali ste " + listOfProducts.size() + " proizvoda!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), "Dodali ste " + listOfProducts.size() + " proizvoda!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void productsListReceived(ArrayList<ProductsModel> productsList) {
        if(productsList != null){
            recyclerView.setAdapter(null);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            selectProductsAdapter = new SelectProductsAdapter(productsList);
            recyclerView.setAdapter(selectProductsAdapter);
        }
    }

}
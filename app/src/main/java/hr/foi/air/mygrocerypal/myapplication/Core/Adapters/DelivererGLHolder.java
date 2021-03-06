package hr.foi.air.mygrocerypal.myapplication.Core.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hr.foi.air.mygrocerypal.myapplication.Core.Listeners.GroceryListOperationListener;
import hr.foi.air.mygrocerypal.myapplication.Core.Enumerators.GroceryListOperation;
import hr.foi.air.mygrocerypal.myapplication.Model.GroceryListsModel;
import hr.foi.air.mygrocerypal.myapplication.R;

public class DelivererGLHolder extends RecyclerView.ViewHolder {

    private static final String CURRENCY = " KN";

    private GroceryListsModel mGroceryListsModel;

    private TextView mStore;
    private TextView mOrderID;
    private TextView mOrderDate;
    private TextView mPrice;
    private TextView mCommision;
    private TextView mAddress;
    private TextView mDeliveryTown;
    private View view;

    private Button btnAcceptGL;
    private Button btnIgnoreGL;
    private Button btnReturnGL;

    private GroceryListOperationListener mGroceryListOperationListener;

    /**
     * Proslijedi fragmentu odabranu instancu GroceryListModel-a
     */
    private View.OnClickListener btnGroceryListDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGroceryListOperationListener.buttonPressedOnGroceryList(mGroceryListsModel, GroceryListOperation.DETAILS);
        }
    };

    /**
     * Gumbovi na cardview (PRIHVATI, IGNORIRAJ, VRATI)
     */
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.accept_gl:
                    mGroceryListOperationListener.buttonPressedOnGroceryList(mGroceryListsModel, GroceryListOperation.ACCEPT);
                    break;
                case R.id.ignore_gl:
                    mGroceryListOperationListener.buttonPressedOnGroceryList(mGroceryListsModel, GroceryListOperation.IGNORE);
                    break;
                case R.id.btnRestoreIgnored: //CASE ZA VRATI
                    mGroceryListOperationListener.buttonPressedOnGroceryList(mGroceryListsModel, GroceryListOperation.RETURN);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Konstruktor
     * @param itemView
     * @param type
     */
    public DelivererGLHolder(@NonNull View itemView, int type) {
        super(itemView);
        view = itemView;

        this.mStore = itemView.findViewById(R.id.store_name);
        this.mOrderID = itemView.findViewById(R.id.order_id);
        this.mOrderDate = itemView.findViewById(R.id.order_date);
        this.mPrice = itemView.findViewById(R.id.price);
        this.mCommision = itemView.findViewById(R.id.commision);
        this.mAddress = itemView.findViewById(R.id.address);
        this.mDeliveryTown = itemView.findViewById(R.id.delivery_town);

        setButtonsListener(type, itemView);
        itemView.setOnClickListener(btnGroceryListDetails);
    }

    /**
     * Povezivanje buttona i metoda
     * @param mType
     * @param mItemView
     */
    private void setButtonsListener(int mType, View mItemView){
        switch (mType){
            case 0: //AKTIVNI GROCERYLISTI
                btnAcceptGL = mItemView.findViewById(R.id.accept_gl);
                btnIgnoreGL = mItemView.findViewById(R.id.ignore_gl);
                btnAcceptGL.setOnClickListener(btnClickListener);
                btnIgnoreGL.setOnClickListener(btnClickListener);
                break;
            case 1:
                //VRATI IGNORIRAI GL
                btnReturnGL = mItemView.findViewById(R.id.btnRestoreIgnored);//PRONADI SVOJ GUMB
                btnReturnGL.setOnClickListener(btnClickListener);//DODAJ MU CLICKLISTENER
                break;
            default:
                break;
        }
    }

    /**
     * Bindanje
     * @param mGroceryListsModel
     * @param mGroceryListOperationListener
     */
    public void bind(GroceryListsModel mGroceryListsModel, GroceryListOperationListener mGroceryListOperationListener){
        this.mGroceryListsModel = mGroceryListsModel;

        this.mStore.setText(mGroceryListsModel.getStore_name());
        this.mOrderID.setText(view.getContext().getResources().getString(R.string.orderID) + "  " + mGroceryListsModel.getGrocerylist_key());
        this.mOrderDate.setText(view.getContext().getResources().getString(R.string.date) + "  " + mGroceryListsModel.getStart_date());
        this.mPrice.setText(view.getContext().getResources().getString(R.string.price) + "  " + mGroceryListsModel.getTotal_price() + CURRENCY);
        this.mCommision.setText(view.getContext().getResources().getString(R.string.commisson) +  "  " + mGroceryListsModel.getCommision() + CURRENCY);
        this.mAddress.setText(view.getContext().getResources().getString(R.string.address) +  "  " + mGroceryListsModel.getDelivery_address());
        this.mDeliveryTown.setText(view.getContext().getResources().getString(R.string.city) +  "  " + mGroceryListsModel.getDelivery_town());

        this.mGroceryListOperationListener = mGroceryListOperationListener;
    }

}

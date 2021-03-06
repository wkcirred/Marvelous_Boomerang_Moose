package marvelousboomerangmoose.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import marvelousboomerangmoose.shoppingwithfriends.Model.Product;
import marvelousboomerangmoose.shoppingwithfriends.Model.ProductActivity;
import marvelousboomerangmoose.shoppingwithfriends.Model.UserActivity;

/**
 * Home activity contains a way to log out as well as a friends list and a way to lookup/add
 * new friends.
 */
public class HomeActivity extends ActionBarActivity {
    //private ArrayList<String> arrayList;
    //private ArrayList<String> interestList;
    // Used to store interestAlert for User
    public static HashMap<String, Product> interestAlert = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HashMap<String, Product> userSalesList = ProductActivity.salesList;
        interestedSalesList(userSalesList, UserActivity.loggedInUser.getItemList());
        ListView myListView = (ListView) this.findViewById(R.id.listView3);
        SaleReportAdapter listAdapter = new SaleReportAdapter(interestAlert);
        myListView.setAdapter(listAdapter);
    }

    /**
     * Creates a list of the user's interested items that have been reported to be on sale.
     */
    @SuppressWarnings("WeakerAccess")
    public void interestedSalesList(HashMap<String, Product> salesMap,
                                    HashMap<String, Product> interestMap) {
        ArrayList<String> arrayList;
        ArrayList<String> interestList;
        if (!salesMap.isEmpty()) {
            arrayList = new ArrayList<>(salesMap.keySet());
            interestList = new ArrayList<>(interestMap.keySet());
            interestAlert = new HashMap<>();
            //for a sale report to show up it needs to be: (interest price >= sale price)
            for (int i = 0; i < arrayList.size(); i++) {
                if (interestList.contains(arrayList.get(i))) {
                    Product sale = salesMap.get(arrayList.get(i));
                    double saleCost = sale.getPrice();
                    Product interest = interestMap.get(arrayList.get(i));
                    double interestCost = interest.getPrice();
                    //add stuff to the new hashMap
                    if (saleCost <= interestCost) {
                        interestAlert.put(arrayList.get(i), salesMap.get(arrayList.get(i)));
                    }
                }
            }
        }
    }

    /**
     * Button click that takes the user to ItemListActivity screen
     * @param v - the button being clicked
     */
    public void buttonItemListOnClick(View v) {
        v.getId();
        startActivity(new Intent(this, ItemListActivity.class));
    }

    /**
     * Button click that takes the user to SalesReportActivity screen
     * @param v - the button being clicked
     */
    public void reportSaleOnClick(View v) {
        v.getId();
        startActivity(new Intent(this, SalesReportActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    /**
     * Logs the user out of the program.
     * @param v the button being clicked
     */
    public void logoutOnClick(View v) {
        v.getId();
        UserActivity.loggedInUser = null;
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Changes the screen to the friends list
     * @param v the button being clicked
     */
    public void activeUsersOnClick(View v) {
        v.getId();
        startActivity(new Intent(this, ActiveUsersListActivity.class));
    }

    /**
     * Changes the screen to the new friend list screen
     * @param v the button being clicked
     */
    public void currentFriendsOnClick(View v) {
        v.getId();
        startActivity(new Intent(this, CurrentFriendListActivity.class));
    }

    /**
     * Changes the screen to the sales map screen
     * @param v the button being clicked
     */
    public void salesMapOnClick(View v) {
        v.getId();
        if (!interestAlert.isEmpty()) {
            startActivity(new Intent(this, SalesMapActivity.class));
        } else {
            Context context = getApplicationContext();
            CharSequence text = "No Sales Map data available.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }*/
}

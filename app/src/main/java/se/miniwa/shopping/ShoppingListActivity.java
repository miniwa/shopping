package se.miniwa.shopping;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import se.miniwa.shopping.item.AddShoppingItemFragment;
import se.miniwa.shopping.item.Item;
import se.miniwa.shopping.item.ShoppingListFragment;

public class ShoppingListActivity extends AppCompatActivity implements
        ShoppingListFragment.ShoppingListFragmentInteractionListener,
        AddShoppingItemFragment.OnAddShoppingItemInteractionListener
{
    private List<Item> shoppingList = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_list:
                    // Change fragment.
                    ShoppingListFragment shoppingListFragment = ShoppingListFragment.newInstance(shoppingList);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_shopping_main_fragment, shoppingListFragment);
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Change fragment.
        ShoppingListFragment shoppingListFragment = ShoppingListFragment.newInstance(shoppingList);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_shopping_main_fragment, shoppingListFragment);
        transaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onItemSatisfiedSwitched(Item item, boolean satisfied) {

    }

    @Override
    public void onAddItemClick() {
        // Change fragment.
        AddShoppingItemFragment addShoppingItem = AddShoppingItemFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_shopping_main_fragment, addShoppingItem);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onShoppingItemAdded(Item item) {
        shoppingList.add(item);

        // Change fragment.
        getSupportFragmentManager().popBackStack();
    }
}

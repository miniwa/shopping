package se.miniwa.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import se.miniwa.shopping.item.AddShoppingItemFragment;
import se.miniwa.shopping.item.Item;
import se.miniwa.shopping.item.ShoppingListFragment;

public class ShoppingListActivity extends AppCompatActivity implements
        ShoppingListFragment.ShoppingListFragmentInteractionListener,
        AddShoppingItemFragment.OnAddShoppingItemInteractionListener
{
    private static final int RC_SIGN_IN = 1337;

    private List<Item> shoppingList = new ArrayList<>();
    private Menu navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("Navigation", "ItemID" + item.getItemId());
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

                case R.id.navigation_signin:
                    // Attempt to authenticate user.
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build()
                    );

                    Intent login = AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build();
                    startActivityForResult(login, RC_SIGN_IN);
                    return false;

                case R.id.navigation_signout:
                    FirebaseAuth.getInstance().signOut();

                    // Toggle sign in/out.
                    navigation.setGroupVisible(R.id.navigation_group_anonymous, true);
                    navigation.setGroupVisible(R.id.navigation_group_authenticated, false);

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast goodbyeToast = Toast.makeText(context,
                            R.string.toast_user_signed_out, duration);

                    // Show toast.
                    goodbyeToast.show();
                    return false;
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

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation = navView.getMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK) {
                // Toggle sign in/out.
                navigation.setGroupVisible(R.id.navigation_group_anonymous, false);
                navigation.setGroupVisible(R.id.navigation_group_authenticated, true);

                // Welcome user.
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String name = user.getDisplayName();
                String format = getString(R.string.toast_user_authenticated_format);
                String text = String.format(format, name);

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast welcomeToast = Toast.makeText(context, text, duration);

                // Show toast.
                welcomeToast.show();
            } else {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast errorToast = Toast.makeText(context,
                        R.string.toast_user_sign_in_failed, duration);

                // Show toast.
                errorToast.show();
            }
        }
    }

    @Override
    public void onItemSatisfiedSwitched(Item item, boolean satisfied) {
        item.satisfied = satisfied;
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

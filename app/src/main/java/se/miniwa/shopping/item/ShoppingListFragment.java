package se.miniwa.shopping.item;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import se.miniwa.shopping.R;

/**
 * A fragment representing a shopping list.
 * <p/>
 * Activities containing this fragment MUST implement the {@link ShoppingListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListFragment extends Fragment {
    public List<Item> shoppingList = null;
    private ShoppingListFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShoppingListFragment newInstance(List<Item> shoppingList) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        fragment.shoppingList = shoppingList;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.shopping_list);
        FloatingActionButton addItemButton = view.findViewById(R.id.shopping_list_add_item_button);

        // Set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ShoppingListRecyclerViewAdapter(shoppingList, listener));

        // Bind click event
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddItemClick();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShoppingListFragmentInteractionListener) {
            listener = (ShoppingListFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ShoppingListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ShoppingListFragmentInteractionListener {
        void onItemSatisfiedSwitched(Item item, boolean satisfied);
        void onAddItemClick();
    }
}

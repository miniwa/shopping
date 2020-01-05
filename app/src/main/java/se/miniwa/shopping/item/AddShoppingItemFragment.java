package se.miniwa.shopping.item;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import se.miniwa.shopping.R;

/**
 * Activities that contain this fragment must implement the
 * {@link AddShoppingItemFragment.OnAddShoppingItemInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddShoppingItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddShoppingItemFragment extends Fragment {
    private OnAddShoppingItemInteractionListener listener;

    public AddShoppingItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddShoppingItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddShoppingItemFragment newInstance() {
        AddShoppingItemFragment fragment = new AddShoppingItemFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shopping_item, container, false);
        final EditText name = view.findViewById(R.id.add_shopping_name);
        final EditText quantity = view.findViewById(R.id.add_shopping_quantity);
        Button addItem = view.findViewById(R.id.add_shopping_button);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    String addedName = name.getText().toString();
                    int addedQuantity = Integer.parseInt(quantity.getText().toString());

                    Item added = new Item();
                    added.name = addedName;
                    added.quantity = addedQuantity;
                    added.satisfied = false;
                    listener.onShoppingItemAdded(added);
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddShoppingItemInteractionListener) {
            listener = (OnAddShoppingItemInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddShoppingItemInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddShoppingItemInteractionListener {
        void onShoppingItemAdded(Item item);
    }
}

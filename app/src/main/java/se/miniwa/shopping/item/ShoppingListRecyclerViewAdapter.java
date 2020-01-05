package se.miniwa.shopping.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import se.miniwa.shopping.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * specified {@link ShoppingListFragment.ShoppingListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {
    private final List<Item> items;
    private final ShoppingListFragment.ShoppingListFragmentInteractionListener listener;

    public ShoppingListRecyclerViewAdapter(List<Item> items, ShoppingListFragment.ShoppingListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.nameView.setText(holder.item.name);
        holder.quantityView.setText(Integer.toString(holder.item.quantity));
        holder.switchView.setChecked(holder.item.satisfied);

        holder.switchView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(listener != null) {
                    listener.onItemSatisfiedSwitched(holder.item, isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Item item;

        public final View view;
        public final TextView nameView;
        public final TextView quantityView;
        public final Switch switchView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.nameView = view.findViewById(R.id.name);
            this.quantityView = view.findViewById(R.id.quantity);
            this.switchView = view.findViewById(R.id.satisfied);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}

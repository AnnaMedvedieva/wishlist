package org.company.annamedvedieva.wishlist.listitems;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.company.annamedvedieva.wishlist.data.Item;
import org.company.annamedvedieva.wishlist.databinding.ItemCardBinding;
import org.company.annamedvedieva.wishlist.itemdetail.ItemDetailActivity;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder> {

    public static final int REQUEST_FOR_DETAIL_ACTIVITY = 4;

    private List<Item> mItemsData;
    private Context mContext;
    private ListItemsViewModel mViewModel;

    /**
     *  @param  context of the app.
     */
    ListItemsAdapter(Context context, ListItemsViewModel viewModel){
        this.mContext = context;
        this.mViewModel = viewModel;
    }

    /**
     *
     * @param viewGroup ViewGroup into which the new View will be added
     *      *               after it is bound to an adapter position.
     * @param i The view type of the new View.
     * @return New ViewHolder.
     */
    @NonNull
    @Override
    public ListItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(viewGroup.getContext());
        ItemCardBinding binding =
                ItemCardBinding.inflate(layoutInflater, viewGroup, false);
        return new ListItemsAdapter.ViewHolder(binding);    }

    @Override
    public void onBindViewHolder(@NonNull ListItemsAdapter.ViewHolder viewHolder, int position) {
        mViewModel.setItem(mItemsData.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item currentItem = mItemsData.get(position);
                Intent detailIntent = new Intent(mContext, ItemDetailActivity.class);
                detailIntent.putExtra("item_id", currentItem.getItemId());
                ((Activity) mContext).startActivityForResult(detailIntent, REQUEST_FOR_DETAIL_ACTIVITY);

            }
        });
        viewHolder.binding.setViewmodel(mViewModel);
        viewHolder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mItemsData != null ? mItemsData.size() : 0;
    }

    public void setData(List<Item> newData) {
        this.mItemsData = newData;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemCardBinding binding;

        public ViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

package org.company.annamedvedieva.wishlist.wishlists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.company.annamedvedieva.wishlist.data.Wishlist;
import org.company.annamedvedieva.wishlist.databinding.ListCardBinding;
import org.company.annamedvedieva.wishlist.listitems.ListItemsActivity;
import java.util.List;

public class WishlistsAdapter extends RecyclerView.Adapter<WishlistsAdapter.ViewHolder> {

    public static final int REQUEST_FOR_ITEMS_ACTIVITY = 1;

    private List<Wishlist> mListsData;
    private Context mContext;
    private WishlistsViewModel mViewModel;

    /**
     * @param context   of the app.
     */
    WishlistsAdapter(Context context, WishlistsViewModel viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;

    }

    /**
     * @param viewGroup ViewGroup into which the new View will be added
     *                  *               after it is bound to an adapter position.
     * @param i         The view type of the new View.
     * @return New ViewHolder.
     */
    @NonNull
    @Override
    public WishlistsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(viewGroup.getContext());
        ListCardBinding binding =
                ListCardBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(binding);    }

    @Override
    public void onBindViewHolder(@NonNull WishlistsAdapter.ViewHolder viewHolder, int position) {

       mViewModel.setWishlist(mListsData.get(position));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wishlist currentList = mListsData.get(position);

                Intent detailIntent = new Intent(mContext, ListItemsActivity.class);
            detailIntent.putExtra("wishlist_id", currentList.getId());
                ((Activity) mContext).startActivityForResult(detailIntent, REQUEST_FOR_ITEMS_ACTIVITY);
            }
        });
        viewHolder.binding.setViewmodel(mViewModel);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mListsData != null ? mListsData.size() : 0;
    }

    public void setData(List<Wishlist> newData) {
        this.mListsData = newData;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ListCardBinding binding;

        public ViewHolder(ListCardBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
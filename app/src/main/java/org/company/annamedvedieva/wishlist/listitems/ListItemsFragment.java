package org.company.annamedvedieva.wishlist.listitems;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import org.company.annamedvedieva.wishlist.databinding.ListItemsFragmentBinding;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListItemsFragment extends Fragment {

    @Inject
    public WishlistRepository mRepository;

    private ListItemsViewModel mViewModel;
    private ListItemsAdapter mAdapter;
    private ListItemsFragmentBinding mBinding;
    private String wishlistId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_items_fragment, container, false);

        mViewModel = ListItemsActivity.createViewModel(getActivity(), mRepository);

        if (mBinding == null) {
            mBinding = ListItemsFragmentBinding.bind(rootView);
        }

        mBinding.setLifecycleOwner(getActivity());
        mBinding.setViewmodel(mViewModel);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view_1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mAdapter = new ListItemsAdapter(mRecyclerView.getContext(), mViewModel);

        mViewModel.getAllItems(getWishlistId()).observe(getViewLifecycleOwner(),items -> {
            if (items.isEmpty()){
                mViewModel.ifEmpty(true);
            }
            else{
                mViewModel.ifEmpty(false);
           mAdapter.setData(items);
        }
        }) ;


        mRecyclerView.setAdapter(mAdapter);

        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void deleteWishlist(String listId){
        mViewModel.deleteWishlist(listId);
    }

    private String getWishlistId(){
        if(getArguments() != null){
             wishlistId = getArguments().getString("wishlist_id");
        }
        return wishlistId;
    }


}

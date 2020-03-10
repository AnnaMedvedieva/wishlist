package org.company.annamedvedieva.wishlist.itemdetail;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import org.company.annamedvedieva.wishlist.databinding.ItemDetailFragmentBinding;

import javax.inject.Inject;

public class ItemDetailFragment extends Fragment {

    private ItemDetailViewModel mViewModel;

    @Inject
    public Context mContext;

    private ItemDetailFragmentBinding mBinding;

    @Inject
    public WishlistRepository mRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail_fragment, container, false);

        mViewModel = ItemDetailActivity.createViewModel(getActivity(),mRepository);

        if(mBinding == null){
            mBinding = ItemDetailFragmentBinding.bind(rootView);
        }

        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(getActivity());

        loadItem();

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void deleteItem(String itemId){
        mViewModel.deleteItem(itemId);
    }

    private void loadItem() {
        if (getArguments() != null) {
            mViewModel.setItem(getArguments().getString("item_id"));
        }
    }
}





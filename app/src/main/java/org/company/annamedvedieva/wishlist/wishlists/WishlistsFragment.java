package org.company.annamedvedieva.wishlist.wishlists;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import org.company.annamedvedieva.wishlist.databinding.ListsFragmentBinding;

import javax.inject.Inject;

public class WishlistsFragment extends Fragment {

    private WishlistsViewModel mViewModel;
    private WishlistsAdapter mAdapter;
    private ListsFragmentBinding mBinding;

    @Inject
    public WishlistRepository mRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.lists_fragment, container, false);

        mViewModel = WishlistsActivity.createViewModel(getActivity(), mRepository);


        if (mBinding == null) {
            mBinding = ListsFragmentBinding.bind(rootView);
        }

        mBinding.setLifecycleOwner(getActivity());
        mBinding.setViewmodel(mViewModel);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mAdapter = new WishlistsAdapter(mRecyclerView.getContext(), mViewModel);

        mViewModel.getAllWishlists().observe(getViewLifecycleOwner(),wishlists -> {
                    if (wishlists.isEmpty()) {
                        mViewModel.ifEmpty(true);
                    } else {
                        mViewModel.ifEmpty(false);
                        mAdapter.setData(wishlists);
                    }
                });
        mRecyclerView.setAdapter(mAdapter);

        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}

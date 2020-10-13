package org.company.annamedvedieva.wishlist.addeditwishlist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import org.company.annamedvedieva.wishlist.databinding.AddEditWishlistFragmentBinding;
import org.company.annamedvedieva.wishlist.wishlists.WishlistImagePagerAdapter;

import javax.inject.Inject;


public class AddEditWishlistFragment extends Fragment {

    @Inject
    public Context mContext;

    private AddEditWishlistFragmentBinding binding;

    private ViewPager viewPager;

    @Inject
    public WishlistRepository mRepository;

    private AddEditWishlistViewModel mViewModel;

    private WishlistImagePagerAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_edit_wishlist_fragment, container, false);

        mViewModel = AddEditWishlistActivity.createViewModel(getActivity(), mRepository);

        if (binding == null) {
            binding = AddEditWishlistFragmentBinding.bind(rootView);
        }

        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(getActivity());

        mAdapter = new WishlistImagePagerAdapter(getActivity());


        viewPager = rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(mAdapter);

        return binding.getRoot();
    }

    public void saveWishlist() {

        chooseImage();
        mViewModel.addOrEditWishlist();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        setupSnackbar();
    }

    private void chooseImage() {
        int image = mAdapter.wishlistImages.get(viewPager.getCurrentItem());
        mViewModel.setImage(image);
    }

    private void loadData() {
        // Add or edit an existing wishlist
        if (getArguments() != null) {
            mViewModel.start(getArguments().getString("wishlist_id"));
        } else {
            mViewModel.start(null);
        }
    }

    private void setupSnackbar(){
        mViewModel.getSnackbarText().observe(getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> event) {
                Integer message = event.getContentIfNotHandled();
                if (message != null) {
                    Snackbar.make(getActivity().findViewById(R.id.add_edit_layout), getString(message), Snackbar.LENGTH_LONG).show();
            }
            }
        });
    }
}
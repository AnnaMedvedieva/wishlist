package org.company.annamedvedieva.wishlist.wishlists;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.company.annamedvedieva.wishlist.LiveDataTestUtil;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.FakeWishlistRepository;
import org.company.annamedvedieva.wishlist.data.Wishlist;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;


public class WishlistsViewModelTest {

    private FakeWishlistRepository wishlistsRepository;

    private WishlistsViewModel mWishlistsViewModel;

    private List<Wishlist> wishlists;


    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setupViewModel() {
        wishlistsRepository = new FakeWishlistRepository();

        wishlists = new ArrayList<>();

        wishlists.add(new Wishlist("1", "Style", R.drawable.style));
        wishlists.add(new Wishlist("2", "Presents", R.drawable.presents));
        wishlists.add(new Wishlist("3", "Home", R.drawable.neutral));

        wishlistsRepository.addWishlists(wishlists);

        mWishlistsViewModel = new WishlistsViewModel(wishlistsRepository);

    }

    @Test
    public void getAllWishLists_notNull() throws InterruptedException {

        assertThat(LiveDataTestUtil.getOrAwaitValue(mWishlistsViewModel.getAllWishlists()), notNullValue());

    }
    @Test
    public void getAllWishLists_getsRightOutput() throws InterruptedException {
        assertThat(LiveDataTestUtil.getOrAwaitValue(mWishlistsViewModel.getAllWishlists()), is(wishlists));
    }

}
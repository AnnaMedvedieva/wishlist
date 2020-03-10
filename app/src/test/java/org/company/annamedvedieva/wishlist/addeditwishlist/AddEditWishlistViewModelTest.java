package org.company.annamedvedieva.wishlist.addeditwishlist;

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
import static org.junit.Assert.*;

public class AddEditWishlistViewModelTest {

    private FakeWishlistRepository wishlistsRepository;

    private AddEditWishlistViewModel mViewModel;

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

        mViewModel = new AddEditWishlistViewModel(wishlistsRepository);
    }

    @Test
    public void addOrEditWishlist() {

    }

    @Test
    public void saveWishlist_wishlistSaved() throws InterruptedException {

        //WHEN add new wishlist
        Wishlist newWishlist = new Wishlist("4", "Test", R.drawable.neutral);

        mViewModel.saveWishlist(newWishlist);

        //THEN updateEvent returns itemSaved
        assertThat(LiveDataTestUtil.getOrAwaitValue(mViewModel.getUpdateEvent()), is(LiveDataTestUtil.getOrAwaitValue(mViewModel.itemSaved)));
    }

    @Test
    public void saveWishlist_emptyTitle_wishlistNotSaved(){


    }

    @Test
    public void getUpdateEvent() {
    }

    @Test
    public void getSnackbarText() {
    }


}
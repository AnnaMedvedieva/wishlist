package org.company.annamedvedieva.wishlist.listitems;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.FakeWishlistRepository;
import org.company.annamedvedieva.wishlist.data.Item;
import org.company.annamedvedieva.wishlist.data.Wishlist;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class ListItemsViewModelTest {

    private FakeWishlistRepository wishlistsRepository;

    private ListItemsViewModel mViewModel;

    private List<Wishlist> wishlists;
    private  List<Item> items;


    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setupViewModel() {
        wishlistsRepository = new FakeWishlistRepository();

        wishlists = new ArrayList<>();
        items = new ArrayList<>();

        wishlists.add(new Wishlist("1", "Style", R.drawable.style));
        wishlists.add(new Wishlist("2", "Presents", R.drawable.presents));
        wishlists.add(new Wishlist("3", "Home", R.drawable.neutral));

        wishlistsRepository.addWishlists(wishlists);

        items.add(new Item("1", "1", "Name1", "Notes1", "Link1", "placeholder"));
        items.add(new Item("2", "1", "Name2", "Notes2", "Link2", "placeholder"));
        items.add(new Item("3", "1", "Name3", "Notes3", "Link3", "placeholder"));

        wishlistsRepository.addItems(items);

        mViewModel = new ListItemsViewModel(wishlistsRepository);
    }

    @Test
    public void deleteWishlist_wishlistDeleted(){

        //WHEN delete wishlist by id
        mViewModel.deleteWishlist("1");

        //THEN getWishlist method returns null
        assertThat(mViewModel.getWishlistById("1"), nullValue());
    }

    @Test
    public void getWishlistById(){

        //WHEN get wishlist
        Wishlist testWishlist = mViewModel.getWishlistById("2");

        //THEN titles match
        assertThat(testWishlist.getTitle(), is("Presents"));

    }

    @Test
    public void getAllItems_notNull(){

        assertThat(mViewModel.getAllItems("1"), notNullValue());

    }



}
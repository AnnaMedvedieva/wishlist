package org.company.annamedvedieva.wishlist.data;

import androidx.lifecycle.LiveData;

import java.util.List;

//Created for testing purposes to make a Repository double
public interface MainRepository {


    LiveData<List<Item>> getAllItems(String listId);

    LiveData<List<Wishlist>> getAllWishlists();

    Wishlist getWishlist(String listId);

    void insertOneWishlist(Wishlist wishlist);

    void deleteWishList(String wishlistId);

    Item getItem(String itemId);

    void insertItem(Item item);

    void deleteItem(String itemId);
}

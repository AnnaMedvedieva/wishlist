package org.company.annamedvedieva.wishlist.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/*
 *Fake repository double to test viewmodels
 */

public class FakeWishlistRepository implements MainRepository {

    private MutableLiveData<List<Wishlist>> observableWishlists = new MutableLiveData<>();

    private LinkedHashMap<String, Wishlist> localWishlists = new LinkedHashMap<>();

    private MutableLiveData<List<Item>> observableItems = new MutableLiveData<>();

    private LinkedHashMap<String, Item> localItems = new LinkedHashMap<>();



    @Override
    public LiveData<List<Item>> getAllItems(String listId) {
        return observableItems;
    }

    @Override
    public LiveData<List<Wishlist>> getAllWishlists() {
        return observableWishlists;
    }

    @Override
    public Wishlist getWishlist(String listId) {
        return localWishlists.get(listId);
    }

    @Override
    public void insertOneWishlist(Wishlist wishlist) { localWishlists.put(wishlist.getId(), wishlist);
    }

    @Override
    public void deleteWishList(String wishlistId) {
        localWishlists.remove(wishlistId);
    }

    @Override
    public Item getItem(String itemId) {
        return localItems.get(itemId);
    }

    @Override
    public void insertItem(Item item) {

        localItems.put(item.getItemId(), item);
    }

    @Override
    public void deleteItem(String itemId) {
        localItems.remove(itemId);
    }

    //Helper methods to prepopulate fake repository
    public void addWishlists(List<Wishlist> wishlists) {

        for(Wishlist i: wishlists){
            localWishlists.put(i.getId(), i);
        }

        List<Wishlist> values = new ArrayList<>(localWishlists.values());

        observableWishlists.setValue(values);
    }

    public void  addItems(List<Item> items){

        for(Item i: items){
            localItems.put(i.getItemId(), i);
        }

        List<Item> values = new ArrayList<>(localItems.values());

        observableItems.setValue(values);
    }



}

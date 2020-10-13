package org.company.annamedvedieva.wishlist.listitems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.company.annamedvedieva.wishlist.data.Item;
import org.company.annamedvedieva.wishlist.data.MainRepository;
import org.company.annamedvedieva.wishlist.data.Wishlist;

import java.util.List;

import javax.inject.Inject;

public class ListItemsViewModel extends ViewModel {

    public MainRepository mRepository;
    private LiveData<List<Item>> allItems;

    public MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();

    public MutableLiveData<Item> item = new MutableLiveData<>();
    private MutableLiveData<String> mItemId = new MutableLiveData<>();

    @Inject
    public ListItemsViewModel(MainRepository rep){
        this.mRepository = rep;
    }

    public LiveData<List<Item>> getAllItems(String listId){
        allItems = mRepository.getAllItems(listId);
        return allItems;
    }

    public void deleteWishlist(String wishlistId){
        mRepository.deleteWishList(wishlistId);
    }

    public Wishlist getWishlistById(String listId){
        return mRepository.getWishlist(listId);
    }

    public void setItem(Item newItem) {
        item.setValue(newItem);
    }

    public MutableLiveData<String> getItemId() {
        return mItemId;
    }

    public void ifEmpty(Boolean empty){
        isEmpty.setValue(empty);
    }

}

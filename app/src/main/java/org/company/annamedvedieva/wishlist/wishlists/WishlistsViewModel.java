package org.company.annamedvedieva.wishlist.wishlists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.company.annamedvedieva.wishlist.data.Wishlist;
import org.company.annamedvedieva.wishlist.data.MainRepository;

import java.util.List;

import javax.inject.Inject;

public class WishlistsViewModel extends ViewModel {

    private MainRepository mRepository;

    public MutableLiveData<Wishlist> wishList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();

    @Inject
    public WishlistsViewModel(MainRepository rep){
    this.mRepository = rep;
}

    public LiveData<List<Wishlist>> getAllWishlists() {
        return  mRepository.getAllWishlists();
}

    public void setWishlist(Wishlist newWishlist){
         wishList.setValue(newWishlist);
    }

    public void ifEmpty(Boolean empty){
        isEmpty.setValue(empty);
    }
}

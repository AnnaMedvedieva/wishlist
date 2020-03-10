package org.company.annamedvedieva.wishlist.addeditwishlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.MainRepository;
import org.company.annamedvedieva.wishlist.data.Wishlist;

import javax.inject.Inject;

public class AddEditWishlistViewModel extends ViewModel {

    public MainRepository mRepository;

    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<Integer> image = new MutableLiveData<>();
    public final MutableLiveData<Event<Object>> itemSaved = new MutableLiveData<>();
    public final MutableLiveData<Event<Integer>> mSnackbarText = new MutableLiveData<>();
    private String mWishlistId;

    @Inject
    public AddEditWishlistViewModel(MainRepository rep){
        this.mRepository = rep;
    }


    public void addOrEditWishlist(){

        if(empty(title.getValue())){
            mSnackbarText.setValue(new Event<>(R.string.empty_item));
            return;
        }

        if(mWishlistId == null){
            Wishlist newWishlist = new Wishlist(title.getValue(),image.getValue());
            saveWishlist(newWishlist);
        }

        else {
            Wishlist wishlist = new Wishlist(mWishlistId, title.getValue(),image.getValue());
            saveWishlist(wishlist);
        }
    }

    public void saveWishlist(Wishlist wishlist){
        mRepository.insertOneWishlist(wishlist);
        itemSaved.setValue(new Event<>(new Object()));
    }

    public void start(String wishlistId){
        mWishlistId = wishlistId;

        if(mWishlistId == null){
            return;
        }

        Wishlist wishlist = mRepository.getWishlist(wishlistId);
        title.setValue(wishlist.getTitle());
        image.setValue(wishlist.getImageResource());
    }

    private static boolean empty( final String s ) {
        return s == null || s.trim().isEmpty();
    }

    public LiveData<Event<Object>> getUpdateEvent(){
        return itemSaved;
    }

    public LiveData<Event<Integer>> getSnackbarText(){
        return mSnackbarText;
    }

    public void setImage(int imageResource){
        image.setValue(imageResource);
    }


}

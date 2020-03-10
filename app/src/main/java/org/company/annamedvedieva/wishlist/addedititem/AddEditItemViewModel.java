package org.company.annamedvedieva.wishlist.addedititem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.Item;
import org.company.annamedvedieva.wishlist.data.MainRepository;

import javax.inject.Inject;


public class AddEditItemViewModel extends ViewModel {

    public MainRepository mRepository;

    public final MutableLiveData <String> title = new MutableLiveData<>();
    public final MutableLiveData<String> notes = new MutableLiveData<>();
    public final MutableLiveData<String> image = new MutableLiveData<>();
    public final MutableLiveData<String> link = new MutableLiveData<>();
    private final MutableLiveData<String> wishListId = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> mItemUpdated = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mSnackbarText = new MutableLiveData<>();

    private String mItemId;

    @Inject
    public AddEditItemViewModel(MainRepository repository){
        this.mRepository= repository;
    }

    public void createOrUpdateItem(String listId){

        if(empty(title.getValue())){
            mSnackbarText.setValue(new Event<> (R.string.empty_item));
            return;
        }

        if(mItemId == null){
            Item item = new Item(listId, title.getValue(), notes.getValue(), link.getValue(), image.getValue());
            saveItem(item);
        }

        if(mItemId != null){
            Item item = new Item(mItemId, wishListId.getValue(), title.getValue(), notes.getValue(), link.getValue(), image.getValue());
            saveItem(item);
        }

    }



    private void saveItem(Item item){
        mRepository.insertItem(item);
        mItemUpdated.setValue(new Event<>(new Object()));
    }

    public void start(String itemId) {
        mItemId = itemId;

        if (itemId == null) {
            return;
        }

        Item item = mRepository.getItem(itemId);
        title.setValue(item.getItemTitle());
        notes.setValue(item.getNotes());
        image.setValue(item.getImageResource());
        link.setValue(item.getLink());
        wishListId.setValue(item.getListId());

    }

    public LiveData<Event<Integer>> getSnackbarMessage() {
        return mSnackbarText;
    }

    public LiveData<Event<Object>> getUpdateEvent(){ return mItemUpdated; }


    private static boolean empty( final String s ) {
        return s == null || s.trim().isEmpty();
    }

    public void setImage(String imageResource){
        image.setValue(imageResource);
    }

}

package org.company.annamedvedieva.wishlist.itemdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.data.Item;
import org.company.annamedvedieva.wishlist.data.MainRepository;

import javax.inject.Inject;

public class ItemDetailViewModel extends ViewModel {

    public MainRepository mRepository;

    public final MutableLiveData<Item> item = new MutableLiveData<>();

    public final MutableLiveData<Event<Object>> itemDeleted = new MutableLiveData<>();

    @Inject
    public ItemDetailViewModel(MainRepository repository){
        this.mRepository = repository;
    }

    public void setItem(String itemId){
        Item newItem = mRepository.getItem(itemId);
        item.setValue(newItem);

    }


    public void deleteItem(String itemId){
        mRepository.deleteItem(itemId);
        itemDeleted.setValue(new Event<>(new Object()));
    }

    public LiveData<Event<Object>> getDeleteEvent(){
        return itemDeleted;
    }
}

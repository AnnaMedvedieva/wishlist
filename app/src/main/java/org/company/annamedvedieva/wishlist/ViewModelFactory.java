package org.company.annamedvedieva.wishlist;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.company.annamedvedieva.wishlist.addedititem.AddEditItemViewModel;
import org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistViewModel;
import org.company.annamedvedieva.wishlist.data.MainRepository;
import org.company.annamedvedieva.wishlist.itemdetail.ItemDetailViewModel;
import org.company.annamedvedieva.wishlist.listitems.ListItemsViewModel;
import org.company.annamedvedieva.wishlist.wishlists.WishlistsViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    public final MainRepository repository;

    @Inject
    public ViewModelFactory(MainRepository repository) {

        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WishlistsViewModel.class)) {
            return (T) new WishlistsViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(AddEditWishlistViewModel.class)){
            return (T) new AddEditWishlistViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(ListItemsViewModel.class)){
            return (T) new ListItemsViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(AddEditItemViewModel.class)){
            return (T) new AddEditItemViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(ItemDetailViewModel.class)){
            return (T) new ItemDetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class" + modelClass.getName());
    }

}

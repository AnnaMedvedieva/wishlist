package org.company.annamedvedieva.wishlist.di;

import org.company.annamedvedieva.wishlist.addedititem.AddEditItemActivity;
import org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistActivity;
import org.company.annamedvedieva.wishlist.itemdetail.ItemDetailActivity;
import org.company.annamedvedieva.wishlist.listitems.ListItemsActivity;
import org.company.annamedvedieva.wishlist.wishlists.WishlistsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract WishlistsActivity bindListsActivity();

    @ContributesAndroidInjector
    abstract ListItemsActivity bindListItemsActivity();

    @ContributesAndroidInjector
    abstract ItemDetailActivity bindItemDetailActivity();

    @ContributesAndroidInjector
    abstract AddEditWishlistActivity bindAddEditWishlistActivity();

    @ContributesAndroidInjector
    abstract AddEditItemActivity bindAddEditItemActivity();
}

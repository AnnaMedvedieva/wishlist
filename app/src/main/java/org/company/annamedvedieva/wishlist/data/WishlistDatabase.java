package org.company.annamedvedieva.wishlist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import javax.inject.Singleton;

@Singleton
@Database(entities = {Wishlist.class, Item.class}, version = 1, exportSchema = false)
public abstract class WishlistDatabase extends RoomDatabase {

    public abstract WishlistDao wishlistDao();
    public abstract ItemDao itemDao();

}

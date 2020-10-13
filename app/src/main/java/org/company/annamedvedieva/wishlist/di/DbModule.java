package org.company.annamedvedieva.wishlist.di;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.ItemDao;
import org.company.annamedvedieva.wishlist.data.Wishlist;
import org.company.annamedvedieva.wishlist.data.WishlistDao;
import org.company.annamedvedieva.wishlist.data.WishlistDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    private static final String TAG = "DbModule";

    public static WishlistDatabase mWishlistDatabase;

    @Inject
    public void buildDatabase(Context context){
        mWishlistDatabase = Room.databaseBuilder(context,
                WishlistDatabase.class, "wishlist_db").allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                mWishlistDatabase.wishlistDao().insertLists(prepopulateLists());
                            }
                        });

                    }
                })
                .build();
        Log.d(TAG, "DbModule: dbbuilt");
    }

    public List<Wishlist> prepopulateLists(){
        List<Wishlist> wishlistList = new ArrayList<>();
        wishlistList.add(new Wishlist( "Style", R.drawable.style));
        wishlistList.add(new Wishlist("Presents", R.drawable.presents));
        wishlistList.add(new Wishlist("Home", R.drawable.neutral));
        return wishlistList;
    }


    @Singleton
    @Inject
    @Provides
    public WishlistDatabase providesWishlistDb(Context context){
        buildDatabase(context);
        return mWishlistDatabase;
    }


    @Singleton
    @Provides
    public static ItemDao providesItemDao(WishlistDatabase db) {
        return db.itemDao();
    }

    @Singleton
    @Provides
    public static WishlistDao providesWishlistDao(WishlistDatabase db){
        return db.wishlistDao();
    }
}

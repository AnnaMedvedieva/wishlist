package org.company.annamedvedieva.wishlist.data;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class WishlistRepository implements MainRepository {

    private ItemDao mItemDao;
    private WishlistDao mWishlistDao;

    @Inject
    WishlistRepository ( WishlistDao wDao, ItemDao itemDao){
        this.mWishlistDao = wDao;
        this.mItemDao = itemDao;
    }

    @Override
    public LiveData<List<Item>> getAllItems(String listId){
        return mItemDao.loadItemsOfList(listId);
}
    @Override
    public LiveData<List<Wishlist>> getAllWishlists(){
        return mWishlistDao.getLists();
    }

    @Override
    public Wishlist getWishlist(String listId){
        return mWishlistDao.getWishlistById(listId);
    }


    @Override
    public void insertOneWishlist(Wishlist wishlist){
        new insertWishlistAsyncTask(mWishlistDao).execute(wishlist);
    }

    @Override
    public void deleteWishList(String wishlistId){
        new deleteWishlistAsyncTask(mWishlistDao).execute(wishlistId);

    }

    @Override
    public Item getItem(String itemId){
        return mItemDao.getItem(itemId);
    }

    @Override
    public void insertItem(Item item){
        new insertItemAsyncTask(mItemDao).execute(item);
    }

    @Override
    public void deleteItem(String itemId){
        new deleteItemAsyncTask(mItemDao).execute(itemId);

    }

    private static class insertItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao asyncTaskDao;

        insertItemAsyncTask(ItemDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Item... params) {
            asyncTaskDao.insertItem(params[0]);
            return null;
        }
    }


    private static class deleteItemAsyncTask extends AsyncTask<String, Void, Void> {
        private ItemDao mAsyncTaskDao;

        deleteItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteItemById(params[0]);
            return null;
        }
    }

    private static class insertWishlistAsyncTask extends AsyncTask<Wishlist, Void, Void> {

        private WishlistDao asyncTaskDao;

        insertWishlistAsyncTask(WishlistDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Wishlist... params) {
            asyncTaskDao.insertList(params[0]);
            return null;
        }
    }

    private static class deleteWishlistAsyncTask extends AsyncTask<String, Void, Void> {
        private WishlistDao mAsyncTaskDao;

        deleteWishlistAsyncTask(WishlistDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteListById(params[0]);
            return null;
        }
    }


}

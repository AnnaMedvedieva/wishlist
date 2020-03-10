package org.company.annamedvedieva.wishlist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WishlistDao {

    /**
     * Select all lists from the list table.
     *
     * @return all lists.
     */
    @Query("SELECT * FROM Wishlists")
    LiveData<List<Wishlist>> getLists();

    /**
     * Insert a list in the database.
     *
     * @param list the list to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(Wishlist list);

    @Insert
    void insertLists(List<Wishlist> lists);

    /**
     * Update a list.
     *
     * @param list list to be updated
     */
    @Update
    int updateList(Wishlist list);

    @Query("SELECT * FROM Wishlists WHERE id = :listId")
    Wishlist getWishlistById(String listId);


    /**
     * Delete a list by id.
     *
     */
    @Query("DELETE FROM Wishlists WHERE id = :listId")
    int deleteListById(String listId);

}

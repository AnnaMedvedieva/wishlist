package org.company.annamedvedieva.wishlist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    /**
     * Get an item by id.
     *
     * @return selected item
     */
    @Query("SELECT * FROM Items WHERE itemid=:itemId")
    Item getItem(String itemId);

    @Query("SELECT * FROM Items WHERE listid =:listId")
    LiveData<List<Item>> loadItemsOfList(String listId);

    /**
     * Insert an item in the database.
     *
     * @param item the item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Insert
    void insertListOfItems(List<Item> items);

    /**
     * Update an item.
     *
     * @param item item to be updated
     *
     */
    @Update
    int updateItem(Item item);


    /**
     * Delete an item by id.
     *
     */
    @Query("DELETE FROM Items WHERE itemid = :itemId")
    int deleteItemById(String itemId);
}

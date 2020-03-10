package org.company.annamedvedieva.wishlist.data;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;



import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Items", foreignKeys = @ForeignKey(entity = Wishlist.class,
        parentColumns = "id",
        childColumns = "listid",
        onDelete = CASCADE,
        onUpdate = CASCADE))

public final class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemid")
    private String mItemId;

    @Nullable
    @ColumnInfo(name = "itemtitle")
    private String mItemTitle;

    @Nullable
    @ColumnInfo(name = "itemimage")
    private String mImageResource;

    @NonNull
    @ColumnInfo (name = "listid")
    private String mListId;

    @Nullable
    @ColumnInfo (name = "notes")
    private String mNotes;

    @Nullable
    @ColumnInfo (name="link")
    private String mLink;

    public Item (@NonNull String mItemId, @NonNull String mListId, @Nullable String mItemTitle, @Nullable String mNotes,
                 @Nullable String mLink, @Nullable String mImageResource){
        this.mItemId = mItemId;
        this.mItemTitle = mItemTitle;
        this.mImageResource = mImageResource;
        this.mListId = mListId;
        this.mNotes = mNotes;
        this.mLink = mLink;
    }

    @Ignore
    public Item( @NonNull String listId, @Nullable String title){
        this.mItemId = UUID.randomUUID().toString();
        this.mItemTitle = title;
        this.mListId = listId;
    }


    @Ignore
    public Item(@NonNull String listId, @Nullable String title, @Nullable String notes,
                @Nullable String link, @Nullable String imageResource){
        this.mItemId = UUID.randomUUID().toString();
        this.mItemTitle = title;
        this.mListId = listId;
        this.mNotes = notes;
        this.mLink = link;
        this.mImageResource = imageResource;
    }

    @Ignore
    public Item(@NonNull String listId, @Nullable String title, @Nullable String notes,@Nullable String link){
        this.mItemId = UUID.randomUUID().toString();
        this.mItemTitle = title;
        this.mListId = listId;
        this.mNotes = notes;
        this.mLink = link;
    }

    @NonNull
    public String getItemId() {return this.mItemId; }

    @Nullable
    public String getItemTitle() {
        return this.mItemTitle;
    }

    @Nullable
    public String getImageResource() {
        return this.mImageResource;
    }

    @NonNull
    public String getListId() {return this.mListId; }

    @Nullable
    public String getLink() {
        return mLink;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

}
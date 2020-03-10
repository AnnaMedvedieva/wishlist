package org.company.annamedvedieva.wishlist.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "Wishlists")
public final class Wishlist {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mId;


    @NonNull
    @ColumnInfo(name = "listtitle")
    private String mTitle;


    @ColumnInfo(name = "listimage")
    private int mImageResource;


    public Wishlist(@NonNull String mId, @NonNull String mTitle, int mImageResource){
        this.mId = mId;
        this.mImageResource = mImageResource;
        this.mTitle = mTitle;
    }

    @Ignore
    public Wishlist(@NonNull String title) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = title;
    }

    @Ignore
    public Wishlist(@NonNull String title, int imageResource) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = title;
        this.mImageResource = imageResource;
    }

    @NonNull
    public String getId() {return this.mId; }

    @NonNull
    public String getTitle() {
        return this.mTitle;
    }

    public int getImageResource() {
        return this.mImageResource;
    }
}

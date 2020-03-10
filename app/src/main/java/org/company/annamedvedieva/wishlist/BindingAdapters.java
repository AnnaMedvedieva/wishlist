package org.company.annamedvedieva.wishlist;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("android:src")
    public static void setWishlistImage(ImageView imageView, int imageResource) {
            Glide.with(imageView.getContext()).load(imageResource).into(imageView);
        }


    @BindingAdapter(value={"android:src", "placeholder"}, requireAll=false)
    public static void setImageResource(ImageView imageView, String imageResource,
                                        Drawable placeHolder) {
        if (imageResource == null) {
            imageView.setImageDrawable(placeHolder);
        } else {
            Glide.with(imageView.getContext()).load(imageResource).placeholder(placeHolder).into(imageView);
        }
    }

//    @BindingAdapter("adapter")
//    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter){
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(adapter);
//    }

}


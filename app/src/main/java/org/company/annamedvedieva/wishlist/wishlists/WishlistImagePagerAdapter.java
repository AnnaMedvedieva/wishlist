package org.company.annamedvedieva.wishlist.wishlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;



import org.company.annamedvedieva.wishlist.R;

import java.util.ArrayList;

import javax.inject.Inject;

public class WishlistImagePagerAdapter extends PagerAdapter {

    public ArrayList<Integer> wishlistImages;

    @Inject
    public Context mContext;


    public WishlistImagePagerAdapter(Context context) {
        mContext = context;
        wishlistImages = new ArrayList<>();
        wishlistImages.add(R.drawable.neutral);
        wishlistImages.add(R.drawable.presents);
        wishlistImages.add(R.drawable.groceries);
        wishlistImages.add(R.drawable.style);
        wishlistImages.add(R.drawable.toys);
        wishlistImages.add(R.drawable.travel);
        wishlistImages.add(R.drawable.arts);
        wishlistImages.add(R.drawable.cosmetics);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View imageLayout = inflater.inflate(R.layout.sliding_image, container, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.wishlist_image);

        imageView.setImageResource(wishlistImages.get(position));
        container.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public int getCount() {
        return wishlistImages.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

}

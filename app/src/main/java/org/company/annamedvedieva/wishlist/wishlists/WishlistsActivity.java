package org.company.annamedvedieva.wishlist.wishlists;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import org.company.annamedvedieva.wishlist.ViewModelFactory;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistActivity;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistActivity.RESULT_ADD_EDIT_WISHLIST_OK;
import static org.company.annamedvedieva.wishlist.listitems.ListItemsActivity.RESULT_DELETE_WISHLIST_OK;
import static org.company.annamedvedieva.wishlist.wishlists.WishlistsAdapter.REQUEST_FOR_ITEMS_ACTIVITY;

public class WishlistsActivity extends AppCompatActivity {
    private static final String TAG = "WishlistsActivity";
    public static final int REQUEST_ADD_WISHLIST = 7;

    private WishlistsFragment mFragment;

    @Inject
    WishlistRepository mRepository;
    @Inject
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_activity);

        AndroidInjection.inject(this);

        createViewModel(this, mRepository);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mFragment = new WishlistsFragment();
        fragmentTransaction.add(R.id.fragment_placeholder, mFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newListIntent = new Intent(mContext, AddEditWishlistActivity.class);
                startActivityForResult(newListIntent, REQUEST_ADD_WISHLIST);
            }
        });
    }

    public static WishlistsViewModel createViewModel(FragmentActivity activity, WishlistRepository repository){
        ViewModelFactory mFactory = new ViewModelFactory(repository);
        return new ViewModelProvider(activity, mFactory).get(WishlistsViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FOR_ITEMS_ACTIVITY){
            switch(resultCode){
                case RESULT_DELETE_WISHLIST_OK:
                    Snackbar.make(findViewById(R.id.wishlists), R.string.wishlist_deleted, Snackbar.LENGTH_LONG).show();
                    break;
                case RESULT_ADD_EDIT_WISHLIST_OK:
                    Snackbar.make(findViewById(R.id.wishlists), R.string.wishlist_saved, Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
        if(requestCode == REQUEST_ADD_WISHLIST){
            if(resultCode == RESULT_ADD_EDIT_WISHLIST_OK){
                Snackbar.make(findViewById(R.id.wishlists), R.string.wishlist_saved, Snackbar.LENGTH_LONG).show();
            }
        }
    }
}

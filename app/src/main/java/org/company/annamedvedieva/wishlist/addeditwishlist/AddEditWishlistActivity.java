package org.company.annamedvedieva.wishlist.addeditwishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.ViewModelFactory;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;

import javax.inject.Inject;
import dagger.android.AndroidInjection;


public class AddEditWishlistActivity extends AppCompatActivity {
    private static final String TAG = "AddEditWishlistActivity";
    public static final int RESULT_ADD_EDIT_WISHLIST_OK = 1;

    private AddEditWishlistFragment mFragment;

    private String wishlistId;
    private AddEditWishlistViewModel mViewModel;

    @Inject
    Context mContext;

    @Inject
    WishlistRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_wishlist_activity);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        AndroidInjection.inject(this);

        mViewModel = createViewModel(this, mRepository);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getIntent().hasExtra("wishlist_id")) {
            wishlistId = getIntent().getStringExtra("wishlist_id");
            setTitle("Edit wishlist");
        }

        Bundle arguments = new Bundle();
        arguments.putString("wishlist_id", wishlistId);

        mFragment = new AddEditWishlistFragment();

        mFragment.setArguments(arguments);

        fragmentTransaction.add(R.id.fragment_container, mFragment);
        fragmentTransaction.commit();

        observeChanges();

        ImageButton saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragment.saveWishlist();
            }

        });

    }

    public static AddEditWishlistViewModel createViewModel(FragmentActivity activity, WishlistRepository repository) {
        ViewModelFactory mFactory = new ViewModelFactory(repository);
        return new ViewModelProvider(activity, mFactory).get(AddEditWishlistViewModel.class);
    }

    public void observeChanges(){
        mViewModel.getUpdateEvent().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> event) {
                if (event.getContentIfNotHandled() != null) {
                    setResult(RESULT_ADD_EDIT_WISHLIST_OK);
                    finish();
                }
            }
        });
    }

}

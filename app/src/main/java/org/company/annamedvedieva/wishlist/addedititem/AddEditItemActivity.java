package org.company.annamedvedieva.wishlist.addedititem;

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

public class AddEditItemActivity extends AppCompatActivity {

    public static final int ADD_EDIT_RESULT_OK = 100;

    private AddEditItemFragment mAddEditItemFragment;

    @Inject
    Context mContext;

    @Inject
    WishlistRepository mRepository;

    private String wishListId;
    private String itemId;

    private AddEditItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_item_activity);

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

        if (getIntent().hasExtra("item_id")) {
            itemId = getIntent().getStringExtra("item_id");
            setTitle("Edit item");
        }
        Bundle arguments = new Bundle();
        arguments.putString("item_id", itemId);

        mAddEditItemFragment = new AddEditItemFragment();

        mAddEditItemFragment.setArguments(arguments);

        fragmentTransaction.add(R.id.fragment_container, mAddEditItemFragment);
        fragmentTransaction.commit();


        if (getIntent().hasExtra("Wishlist_id")) {
            wishListId = getIntent().getStringExtra("Wishlist_id");
        }

        receiveUpdateStatus();

        ImageButton saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddEditItemFragment.saveItem(wishListId);
            }

        });

    }

    private void receiveUpdateStatus(){
        mViewModel.getUpdateEvent().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> objectEvent) {
                if (objectEvent.getContentIfNotHandled() != null){
                        AddEditItemActivity.this.onItemSaved();
                }
            }

        });

    }

    private void onItemSaved() {
        setResult(ADD_EDIT_RESULT_OK);
        finish();

    }

    public static AddEditItemViewModel createViewModel(FragmentActivity activity, WishlistRepository repository){
        ViewModelFactory mFactory = new ViewModelFactory(repository);
        return new ViewModelProvider(activity, mFactory).get(AddEditItemViewModel.class);
    }



}

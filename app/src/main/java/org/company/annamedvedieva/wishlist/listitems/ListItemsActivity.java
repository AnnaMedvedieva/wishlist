package org.company.annamedvedieva.wishlist.listitems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.company.annamedvedieva.wishlist.DeleteDialogFragment;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.ViewModelFactory;
import org.company.annamedvedieva.wishlist.addedititem.AddEditItemActivity;
import org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistActivity;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static org.company.annamedvedieva.wishlist.addedititem.AddEditItemActivity.ADD_EDIT_RESULT_OK;
import static org.company.annamedvedieva.wishlist.addeditwishlist.AddEditWishlistActivity.RESULT_ADD_EDIT_WISHLIST_OK;
import static org.company.annamedvedieva.wishlist.itemdetail.ItemDetailActivity.DELETE_OK;
import static org.company.annamedvedieva.wishlist.itemdetail.ItemDetailActivity.EDIT_OK;
import static org.company.annamedvedieva.wishlist.listitems.ListItemsAdapter.REQUEST_FOR_DETAIL_ACTIVITY;

public class ListItemsActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener  {

    public static final int REQUEST_TO_ADD_NEW_ITEM = 5;
    public static final int REQUEST_TO_EDIT_WISHLIST = 3;

    public static final int RESULT_DELETE_WISHLIST_OK = 2;

    @Inject
    Context mContext;

    private ListItemsViewModel viewModel;

    @Inject
    WishlistRepository mRepository;

    private ListItemsFragment mFragment;
    private String wishListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_items_activity);

        AndroidInjection.inject(this);

        viewModel = createViewModel(this, mRepository);

        setupToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mFragment = new ListItemsFragment();

        Bundle arguments = new Bundle();
        arguments.putString("wishlist_id", wishListId);
        mFragment.setArguments(arguments);

        fragmentTransaction.add(R.id.fragment_placeholder, mFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newItemIntent = new Intent(mContext, AddEditItemActivity.class);
                newItemIntent.putExtra("Wishlist_id", wishListId);
                startActivityForResult(newItemIntent, REQUEST_TO_ADD_NEW_ITEM );
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    public static ListItemsViewModel createViewModel(FragmentActivity activity, WishlistRepository repository) {
        ViewModelFactory mFactory = new ViewModelFactory(repository);
        return new ViewModelProvider(activity, mFactory).get(ListItemsViewModel.class);
    }

    private void setupToolbar() {

                if (getIntent().hasExtra("wishlist_id")) {
                    wishListId = getIntent().getStringExtra("wishlist_id");
                    String wishlistTitle = viewModel.getWishlistById(wishListId).getTitle();
                    int wishlistImage = viewModel.getWishlistById(wishListId).getImageResource();

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(wishlistTitle);
            setSupportActionBar(toolbar);

            ImageView toolbarImage = findViewById(R.id.collapsing_image);
            Glide.with(this)
                    .load(wishlistImage)
                    .into(toolbarImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_FOR_DETAIL_ACTIVITY){
            switch(resultCode){
                case DELETE_OK:
                    Snackbar.make(findViewById(R.id.list_items), R.string.item_deleted, Snackbar.LENGTH_LONG).show();
                    break;
                case EDIT_OK:
                    Snackbar.make(findViewById(R.id.list_items), R.string.item_saved, Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
        if(requestCode == REQUEST_TO_ADD_NEW_ITEM){
            if (resultCode == ADD_EDIT_RESULT_OK){
                Snackbar.make(findViewById(R.id.list_items), R.string.item_saved, Snackbar.LENGTH_LONG).show();
            }
        }
        if(requestCode == REQUEST_TO_EDIT_WISHLIST){
            if(resultCode == RESULT_ADD_EDIT_WISHLIST_OK){
                setResult(RESULT_ADD_EDIT_WISHLIST_OK);
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_delete:
                showDeleteDialog();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(mContext, AddEditWishlistActivity.class);
                intent.putExtra( "wishlist_id",wishListId);
                startActivityForResult(intent, REQUEST_TO_EDIT_WISHLIST);
                break;
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    public void showDeleteDialog(){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DeleteDialogFragment.MESSAGE_ID, mContext.getResources().getString(R.string.delete_wishlist));

        DialogFragment dialog = new DeleteDialogFragment();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        mFragment.deleteWishlist(wishListId);
        setResult(RESULT_DELETE_WISHLIST_OK);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}

package org.company.annamedvedieva.wishlist.itemdetail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.company.annamedvedieva.wishlist.DeleteDialogFragment;
import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.ViewModelFactory;
import org.company.annamedvedieva.wishlist.addedititem.AddEditItemActivity;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import javax.inject.Inject;
import dagger.android.AndroidInjection;

import static org.company.annamedvedieva.wishlist.addedititem.AddEditItemActivity.ADD_EDIT_RESULT_OK;

public class ItemDetailActivity extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener {

    private ItemDetailFragment mItemDetailFragment;

    public static final int REQUEST_FOR_EDIT = 1;
    public static final int EDIT_OK = 2;
    public static final int DELETE_OK = 4;

    private String itemId;

    private ItemDetailViewModel mViewModel;

    @Inject
    public Context mContext;

    @Inject
    public WishlistRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_activity);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        AndroidInjection.inject(this);

        mViewModel = createViewModel(this, mRepository);

        receiveUpdateStatus();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mItemDetailFragment = new ItemDetailFragment();

        if (getIntent().hasExtra("item_id")){
            itemId = getIntent().getStringExtra("item_id");
        }

        Bundle arguments = new Bundle();
        arguments.putString("item_id", itemId);

        mItemDetailFragment.setArguments(arguments);

        fragmentTransaction.add(R.id.fragment_container, mItemDetailFragment);
        fragmentTransaction.commit();




    }

    public static ItemDetailViewModel createViewModel(FragmentActivity activity, WishlistRepository repository){
        ViewModelFactory mFactory = new ViewModelFactory(repository);
        return new ViewModelProvider(activity, mFactory).get(ItemDetailViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_detail, menu);
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
                Intent intent = new Intent(mContext, AddEditItemActivity.class);
                intent.putExtra( "item_id",itemId);
                startActivityForResult(intent, REQUEST_FOR_EDIT);
                break;
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_EDIT) {
            if (resultCode == ADD_EDIT_RESULT_OK) {
                setResult(EDIT_OK);
                finish();
            }
        }
    }

    private void receiveUpdateStatus(){
        mViewModel.getDeleteEvent().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> objectEvent) {
                if (objectEvent.getContentIfNotHandled() != null){
                    setResult(DELETE_OK);
                    finish();
                }
            }

        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        mItemDetailFragment.deleteItem(itemId);
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void showDeleteDialog(){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DeleteDialogFragment.MESSAGE_ID, mContext.getResources().getString(R.string.delete_item));

        DialogFragment dialog = new DeleteDialogFragment();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }

}

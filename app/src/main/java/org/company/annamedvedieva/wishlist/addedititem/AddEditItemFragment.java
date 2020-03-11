package org.company.annamedvedieva.wishlist.addedititem;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.company.annamedvedieva.wishlist.Event;
import org.company.annamedvedieva.wishlist.R;
import org.company.annamedvedieva.wishlist.data.WishlistRepository;
import org.company.annamedvedieva.wishlist.databinding.AddEditItemFragmentBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class AddEditItemFragment extends Fragment {

    private static final String TAG = "AddEditItemFragment";
    public static final int REQUEST_TO_ASK_EXTSTORAGE_PERMISSION = 122;
    public static final int REQUEST_FOR_CAMERA = 123;
    public static final int REQUEST_FOR_GALLERY = 124;

    @Inject
    public Context mContext;
    private AddEditItemFragmentBinding mBinding;
    private AddEditItemViewModel mViewModel;
    private String imageFilePath;
    private Uri photoURI;

    @Inject
    public WishlistRepository mRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_edit_item_fragment, container, false);

        mViewModel = AddEditItemActivity.createViewModel(getActivity(), mRepository);

        if (mBinding == null) {
            mBinding = AddEditItemFragmentBinding.bind(rootView);
        }

        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(getActivity());
        mBinding.setListener(onCreateImage());
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        createSnackbar();
    }


    private View.OnClickListener onCreateImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
                showImageDialog();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Log.d(TAG, "onActivityResult: " + resultCode);
        switch (requestCode) {
            case REQUEST_FOR_CAMERA:
                if (resultCode == RESULT_OK) {
                    mViewModel.setImage(imageFilePath);
                    galleryAddPic();
                }

                break;
            case REQUEST_FOR_GALLERY:
                if (resultCode == RESULT_OK && imageReturnedIntent != null) {
                    mViewModel.setImage(imageReturnedIntent.getData().toString());
                }
                break;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public void saveItem(String wishlistId) {
        mViewModel.createOrUpdateItem(wishlistId);
    }


    private void createSnackbar() {
        mViewModel.getSnackbarMessage().observe(getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> event) {
                Integer message = event.getContentIfNotHandled();
                if (message != null) {
                    Snackbar.make(getActivity().findViewById(R.id.add_edit_item), getString(message), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Create a file to store the image
        Log.d(TAG, "takePicture: start intent");
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d(TAG, "takePicture: error");
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(mContext, "org.company.fileprovider",
                    photoFile);
            Log.d(TAG, "takePicture: " + photoURI);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
            startActivityForResult(takePicture, REQUEST_FOR_CAMERA);
        }
    }


    private void chooseFromTheGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_FOR_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "WishlistApp");

        if (!storageDir.exists()) {
            boolean s = new File(storageDir.getPath()).mkdirs();
            if (!s) {
                Log.v("not", "not created");
            } else {
                Log.v("cr", "directory created");
            }
        } else {
            Log.v("directory", "directory exists");
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",/* suffix */
                storageDir/* directory */
        );

        imageFilePath = image.getAbsolutePath();
        Log.d(TAG, "createImageFile: " + imageFilePath);
        return image;
    }

    private void askPermissionToWriteExtStorage(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TO_ASK_EXTSTORAGE_PERMISSION);
            }
        }
        else {
            takePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_TO_ASK_EXTSTORAGE_PERMISSION) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();

            }
        }
    }


    private void showImageDialog(){
        String [] imageOptions = {"Take picture", "Choose from the gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Take picture ot choose from the gallery")
                .setItems(imageOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                askPermissionToWriteExtStorage();
                                break;
                            case 1:
                                chooseFromTheGallery();
                                break;
                            case 2:
                                break;
                        }
                    }
                });
         builder.show();
    }

    private void loadData() {
        // Add or edit an existing item?
        if (getArguments() != null) {
            Log.d(TAG, "loadData: " + getArguments().getString("item_id"));
            mViewModel.start(getArguments().getString("item_id"));
        } else {
            mViewModel.start(null);
        }
    }
}

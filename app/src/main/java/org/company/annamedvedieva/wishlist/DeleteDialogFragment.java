package org.company.annamedvedieva.wishlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import org.company.annamedvedieva.wishlist.R;


public class DeleteDialogFragment extends DialogFragment {

    public static final String MESSAGE_ID = "message";

    public interface DeleteDialogListener{
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    DeleteDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle message = getArguments();

        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (message != null){
            builder.setMessage(message.getString(MESSAGE_ID));
        }

        if(message == null){
            builder.setMessage("Are you sure?");
        }
        
        builder.setPositiveButton(R.string.delete_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(DeleteDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(DeleteDialogFragment.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DeleteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement DeleteDialogListener");
        }
        }
    }



package com.example.myapplication3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Ошибка")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Нельзя сохранять пустое поле")
                .setPositiveButton("OK", null)

                .create(); }
}
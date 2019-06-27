package com.wilsofts.text_editor;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class InsertLinkDialogFragment extends AppCompatDialogFragment {
    private OnInsertClickListener listener;

    static InsertLinkDialogFragment newInstance() {
        return new InsertLinkDialogFragment();
    }

    void setOnInsertClickListener(@NonNull OnInsertClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.getDialog().getWindow() != null) {
            this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_insert_link, null);

        final AppCompatEditText textToDisplayEditText = view.findViewById(R.id.text_to_display);
        final AppCompatEditText linkToEditText = view.findViewById(R.id.link_to);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(this.getActivity()));
        dialog.setTitle(R.string.title_insert_link);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.insert, (dialog12, which) -> {
            String title = Objects.requireNonNull(textToDisplayEditText.getText()).toString().trim();
            String url = Objects.requireNonNull(linkToEditText.getText()).toString().trim();

            if (this.listener != null) {
                this.listener.onInsertClick(title, url);
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, (dialog1, which) -> dialog1.cancel());

        return dialog.create();
    }

    public interface OnInsertClickListener {
        void onInsertClick(@NonNull String title, @NonNull String url);
    }
}
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

public class InsertTableDialogFragment extends AppCompatDialogFragment {
    private OnInsertClickListener listener;
    static InsertTableDialogFragment newInstance() {
        return new InsertTableDialogFragment();
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
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_insert_table, null);
        final AppCompatEditText columnCountEditText = view.findViewById(R.id.column_count);
        final AppCompatEditText rowCountEditText = view.findViewById(R.id.row_count);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(this.getActivity()));
        dialog.setTitle(R.string.title_insert_table);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.insert, (dialog12, which) -> {
            String colCount = Objects.requireNonNull(columnCountEditText.getText()).toString().trim();
            String rowCount = Objects.requireNonNull(rowCountEditText.getText()).toString().trim();

            if (this.listener != null) {
                this.listener.onInsertClick(Integer.valueOf(colCount), Integer.valueOf(rowCount));
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, (dialog1, which) -> dialog1.cancel());

        return dialog.create();
    }

    public interface OnInsertClickListener {
        void onInsertClick(int colCount, int rowCount);
    }
}
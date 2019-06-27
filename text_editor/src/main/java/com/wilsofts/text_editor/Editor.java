package com.wilsofts.text_editor;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jkcarino.rtexteditorview.RTextEditorButton;
import com.jkcarino.rtexteditorview.RTextEditorView;

public class Editor implements ColorPickerDialogListener {
    private static final String TAG = "RTextEditorView";
    private static final int DIALOG_TEXT_FORE_COLOR_ID = 0;
    private static final int DIALOG_TEXT_BACK_COLOR_ID = 1;
    private final FragmentActivity activity;
    private final RTextEditorView editor;

    public Editor(FragmentActivity activity, RTextEditorView editor) {
        this.activity = activity;
        this.editor = editor;

        // Enable keyboard's incognito mode
        this.editor.setIncognitoModeEnabled(true);

        //  RTextEditorToolbar editorToolbar = findViewById(R.id.editor_toolbar);
        //  editorToolbar.setEditorView(editor);

        // Set initial content
        this.editor.setHtml("<p><b><i><u><strike>" + "The quick brown fox jumps over the lazy dog." + "</strike></u></i></b></p>");

        // Listen to the editor's text changes
        this.editor.setOnTextChangeListener(content -> Log.e(Editor.TAG, "onTextChanged: " + content));
    }

    public Editor insertLink(RTextEditorButton insertLinkButton) {
        insertLinkButton.setOnClickListener(view -> {
            InsertLinkDialogFragment dialog = InsertLinkDialogFragment.newInstance();
            dialog.setOnInsertClickListener(this.editor::insertLink);
            dialog.show(this.activity.getSupportFragmentManager(), "insert-link-dialog");
        });
        return this;
    }

    public Editor setTextColor(RTextEditorButton textForeColorButton) {
        textForeColorButton.setOnClickListener(view -> ColorPickerDialog.newBuilder()
                .setDialogId(Editor.DIALOG_TEXT_FORE_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity));
        return this;
    }

    public Editor setFillColor(RTextEditorButton textBackColorButton) {
        textBackColorButton.setOnClickListener(view -> ColorPickerDialog.newBuilder()
                .setDialogId(Editor.DIALOG_TEXT_BACK_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_back_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity));
        return this;
    }

    public Editor insertTable(RTextEditorButton insertTableButton){
        insertTableButton.setOnClickListener(view -> {
            InsertTableDialogFragment dialog = InsertTableDialogFragment.newInstance();
            dialog.setOnInsertClickListener(this.editor::insertTable);
            dialog.show(this.activity.getSupportFragmentManager(), "insert-table-dialog");
        });
        return this;
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        if (dialogId == Editor.DIALOG_TEXT_FORE_COLOR_ID) {
            this.editor.setTextColor(color);
        } else if (dialogId == Editor.DIALOG_TEXT_BACK_COLOR_ID) {
            this.editor.setTextBackgroundColor(color);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
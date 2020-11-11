package com.rey.material.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    protected static final String ARG_BUILDER = "arg_builder";
    private View.OnClickListener mActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (DialogFragment.this.mBuilder != null) {
                if (v.getId() == Dialog.ACTION_POSITIVE) {
                    DialogFragment.this.mBuilder.onPositiveActionClicked(DialogFragment.this);
                } else if (v.getId() == Dialog.ACTION_NEGATIVE) {
                    DialogFragment.this.mBuilder.onNegativeActionClicked(DialogFragment.this);
                } else if (v.getId() == Dialog.ACTION_NEUTRAL) {
                    DialogFragment.this.mBuilder.onNeutralActionClicked(DialogFragment.this);
                }
            }
        }
    };
    protected Builder mBuilder;

    public interface Builder {
        Dialog build(Context context);

        void onCancel(DialogInterface dialogInterface);

        void onDismiss(DialogInterface dialogInterface);

        void onNegativeActionClicked(DialogFragment dialogFragment);

        void onNeutralActionClicked(DialogFragment dialogFragment);

        void onPositiveActionClicked(DialogFragment dialogFragment);
    }

    public static DialogFragment newInstance(Builder builder) {
        DialogFragment fragment = new DialogFragment();
        fragment.mBuilder = builder;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = this.mBuilder;
        Dialog dialog = builder == null ? new Dialog(getActivity()) : builder.build(getActivity());
        dialog.positiveActionClickListener(this.mActionListener).negativeActionClickListener(this.mActionListener).neutralActionClickListener(this.mActionListener);
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && this.mBuilder == null) {
            this.mBuilder = (Builder) savedInstanceState.getParcelable(ARG_BUILDER);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Builder builder = this.mBuilder;
        if (builder != null && (builder instanceof Parcelable)) {
            outState.putParcelable(ARG_BUILDER, (Parcelable) builder);
        }
    }

    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null && (dialog instanceof Dialog)) {
            ((Dialog) dialog).dismissImmediately();
        }
        super.onDestroyView();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        this.mBuilder.onCancel(dialog);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.mBuilder.onDismiss(dialog);
    }
}

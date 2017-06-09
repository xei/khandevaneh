package ir.iconish.khandevaneh.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import ir.iconish.khandevaneh.R;

/**
 * Created by hamidreza on 5/8/17.
 * All rights reserved by Digikala.
 */

public class KhandevanehDialog extends Dialog{

    public KhandevanehDialog(@NonNull Context context) {
        super(context);
    }

    public KhandevanehDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected KhandevanehDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public KhandevanehDialog(@NonNull Context context, String alertText, View.OnClickListener onOkBtnClickListener) {
        super(context);
        init(alertText, onOkBtnClickListener);
    }

    private void init(String alertText, final View.OnClickListener onOkBtnClickListener) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_khandevaneh, null, false);
        XeiTextView alertTextView = (XeiTextView) view.findViewById(R.id.dialogKhandevaneh_xeiTextView_alert);
        XeiButton okButton = (XeiButton) view.findViewById(R.id.dialogKhandevaneh_xeiButton_ok);

        alertTextView.setText(alertText);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOkBtnClickListener != null) {
                    onOkBtnClickListener.onClick(view);
                }
                dismiss();
            }
        });

        setContentView(view);
    }

}

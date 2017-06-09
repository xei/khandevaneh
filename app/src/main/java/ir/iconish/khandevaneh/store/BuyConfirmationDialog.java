package ir.iconish.khandevaneh.store;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.view.XeiButton;
import ir.iconish.khandevaneh.view.XeiEditText;
import ir.iconish.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/8/17.
 * All rights reserved by Digikala.
 */

public class BuyConfirmationDialog extends Dialog{

    public BuyConfirmationDialog(@NonNull Context context) {
        super(context);
    }

    public BuyConfirmationDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected BuyConfirmationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public BuyConfirmationDialog(@NonNull Context context, View.OnClickListener onOkBtnClickListener) {
        super(context);
        init(onOkBtnClickListener);
    }

    private void init(final View.OnClickListener onOkBtnClickListener) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_buy_confirmation, null, false);
        final XeiEditText buyConfirmationEditText = (XeiEditText) view.findViewById(R.id.dialogBuyConfirmation_xeiEditText_confirmationCode);
        XeiButton okButton = (XeiButton) view.findViewById(R.id.dialogBuyConfirmation_xeiButton_ok);

//        alertTextView.setText(alertText);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOkBtnClickListener != null) {
                    view.setTag(buyConfirmationEditText.getText().toString());
                    onOkBtnClickListener.onClick(view);
                }
                dismiss();
            }
        });

        setContentView(view);
    }

}

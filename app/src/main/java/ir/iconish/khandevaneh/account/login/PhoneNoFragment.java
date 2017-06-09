package ir.iconish.khandevaneh.account.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.view.XeiButton;
import ir.iconish.khandevaneh.view.XeiEditText;

public class PhoneNoFragment extends Fragment implements View.OnClickListener {

    private XeiEditText mPhoneNoEditText;
    private XeiButton mSendButton;

    private OnSendButtonClickListener mSendButtonClickListener;

    public PhoneNoFragment() {
        // Required empty public constructor
    }

    public static PhoneNoFragment newInstance() {
        PhoneNoFragment fragment = new PhoneNoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_no, container, false);

        findViews(view);
        setOnClickListeners();

        return view;
    }

    private void findViews(View rootView) {
        mPhoneNoEditText = (XeiEditText) rootView.findViewById(R.id.fragmentPhoneNo_xeiEditText_phoneNo);
        mSendButton = (XeiButton) rootView.findViewById(R.id.fragmentPhoneNo_xeiButton_send);
    }

    private void setOnClickListeners() {
        mPhoneNoEditText.setOnClickListener(this);
        mSendButton.setOnClickListener(this);
    }

    public void onSendButtonPressed(String phoneNo) {
        if (phoneNo == null || phoneNo.isEmpty()) {
            Toast.makeText(getContext(), "لطفا شماره موبایل خود را وارد کنید!", Toast.LENGTH_SHORT).show();
        } else if (phoneNo.trim().length() != 11) {
            Toast.makeText(getContext(), "شماره وارد شده نامعتبر است!", Toast.LENGTH_SHORT).show();
        } else if (!phoneNo.trim().substring(0, 3).equals("091") && !phoneNo.trim().substring(0, 3).equals("099")) {
            Toast.makeText(getContext(), "برای استفاده از این اپلیکیشن نیاز به سیم‌کارت همراه اول دارید", Toast.LENGTH_SHORT).show();
        } else if (mSendButtonClickListener != null) {
            mSendButtonClickListener.onSendPhoneNoButtonClick(phoneNo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnSendButtonClickListener) {
            mSendButtonClickListener = (OnSendButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSendButtonClickListener = null;
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.fragmentPhoneNo_xeiButton_send:
                onSendButtonPressed(mPhoneNoEditText.getText().toString());
                break;

            default:
        }
    }

    interface OnSendButtonClickListener {
        void onSendPhoneNoButtonClick(String phoneNo);
    }
}

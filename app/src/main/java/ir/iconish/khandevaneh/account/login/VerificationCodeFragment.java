package ir.iconish.khandevaneh.account.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.view.XeiButton;
import ir.iconish.khandevaneh.view.XeiEditText;


public class VerificationCodeFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PHONE_NO = "ARG_PHONE_NO";

    private XeiEditText mVerificationCodeEditText;
    private XeiButton mSendButton;

    private String mPhoneNo;

    private OnSendButtonClickListener mOnSendButtonClickListener;

    public VerificationCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phoneNo Parameter 1.
     * @return A new instance of fragment VerificationCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificationCodeFragment newInstance(String phoneNo) {
        VerificationCodeFragment fragment = new VerificationCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHONE_NO, phoneNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoneNo = getArguments().getString(ARG_PHONE_NO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);

        findViews(view);
        setOnClickListeners();

        return view;
    }

    private void findViews(View rootView) {
        mVerificationCodeEditText = (XeiEditText) rootView.findViewById(R.id.fragmentVerificationCode_xeiEditText_verificationCode);
        mSendButton = (XeiButton) rootView.findViewById(R.id.fragmentVerificationCode_xeiButton_send);
    }

    private void setOnClickListeners() {
        mVerificationCodeEditText.setOnClickListener(this);
        mSendButton.setOnClickListener(this);
    }

    public void onSendButtonPressed(String verificationCode) {
        if (mOnSendButtonClickListener != null) {
            mOnSendButtonClickListener.onSendVerificationCodeButtonClick(mPhoneNo, verificationCode);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSendButtonClickListener) {
            mOnSendButtonClickListener = (OnSendButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSendButtonClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnSendButtonClickListener = null;
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.fragmentVerificationCode_xeiButton_send:
                onSendButtonPressed(mVerificationCodeEditText.getText().toString());
                break;

            default:
        }
    }

    interface OnSendButtonClickListener {
        void onSendVerificationCodeButtonClick(String phoneNo, String verificationCode);
    }
}

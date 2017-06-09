package ir.iconish.khandevaneh.account.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.iconish.khandevaneh.R;
import ir.iconish.khandevaneh.helper.HelperFunctions;
import ir.iconish.khandevaneh.view.KhandevanehDialog;
import ir.iconish.khandevaneh.view.XeiButton;
import ir.iconish.khandevaneh.view.XeiEditText;
import ir.iconish.khandevaneh.view.XeiTextView;

public class UserInfoFragment extends Fragment {

    private static final String ARG_WHICH_FRAGMENT = "ARG_WHICH_FRAGMENT";

    public static final int WHICH_FRAGMENT_FIRST_NAME = 0;
    public static final int WHICH_FRAGMENT_LAST_NAME = 1;
    public static final int WHICH_FRAGMENT_AVATAR = 2;
    public static final int WHICH_FRAGMENT_EMAIL_ADDRESS = 3;
    public static final int WHICH_FRAGMENT_POSTAL_ADDRESS = 4;

    private int mWhichFragment;

    private XeiTextView mLabelTextView;
    private XeiEditText mValueEditText;
    private XeiButton mNextButton;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance(int whichFragment) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_WHICH_FRAGMENT, whichFragment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWhichFragment = getArguments().getInt(ARG_WHICH_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        mLabelTextView = (XeiTextView) view.findViewById(R.id.fragmentUserInfo_xeiTextView_label);
        mValueEditText = (XeiEditText) view.findViewById(R.id.fragmentUserInfo_xeiTextView_value);
        mNextButton = (XeiButton) view.findViewById(R.id.fragmentUserInfo_xeiButton_next);

        mLabelTextView.setText(selectLabel());
        mNextButton.setText(selectButtonText());

        if (mWhichFragment == WHICH_FRAGMENT_EMAIL_ADDRESS) {
            changeEditTextAttributesForEmailAddress();
        } else if (mWhichFragment == WHICH_FRAGMENT_POSTAL_ADDRESS) {
            changeEditTextAttributesForPostalAddress();
        }

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = mValueEditText.getText().toString();
                if (isMandatory()) {
                    if (!value.isEmpty()) {
                        ((ProfileCompletionActivity)getActivity()).onInfoEnter(mWhichFragment, value);
                    } else {
                        new KhandevanehDialog(getContext(), "پر کردن این فیلد الزامیه رفیق!", null).show();
                    }
                } else {
                    ((ProfileCompletionActivity)getActivity()).onInfoEnter(mWhichFragment, value);
                }
                
            }
        });

        return view;
    }

    private void changeEditTextAttributesForEmailAddress() {
        mValueEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    private void changeEditTextAttributesForPostalAddress() {
        mValueEditText.getLayoutParams().height = HelperFunctions.dpToPx(getContext(), 300);
        int padding = HelperFunctions.dpToPx(getContext(), 16);
        mValueEditText.setPadding(padding, padding, padding, padding);
        mValueEditText.setSingleLine(false);
        mValueEditText.setLines(10);
        mValueEditText.setGravity(Gravity.RIGHT | Gravity.TOP);
    }

    private String selectLabel() {
        switch (mWhichFragment) {
            case WHICH_FRAGMENT_FIRST_NAME:
                return "نام خود را وارد کنید (الزامی)";

            case WHICH_FRAGMENT_LAST_NAME:
                return "نام خانوادگی خود را وارد کنید (الزامی)";

            case WHICH_FRAGMENT_EMAIL_ADDRESS:
                return "آدرس ایمیل خود را وارد کنید (اختیاری)";

            case WHICH_FRAGMENT_POSTAL_ADDRESS:
                return "آدرس پستی خود را وارد کنید (اختیاری)";

            default:
                return "";
        }
    }

    private String selectButtonText() {
        switch (mWhichFragment) {
            case WHICH_FRAGMENT_FIRST_NAME:
                return "بعدی";

            case WHICH_FRAGMENT_LAST_NAME:
                return "بعدی";

            case WHICH_FRAGMENT_EMAIL_ADDRESS:
                return "بعدی";

            case WHICH_FRAGMENT_POSTAL_ADDRESS:
                return "ثبت اطلاعات";

            default:
                return "";
        }
    }

    private boolean isMandatory() {
        switch (mWhichFragment) {
            case WHICH_FRAGMENT_FIRST_NAME:
                return true;

            case WHICH_FRAGMENT_LAST_NAME:
                return true;

            case WHICH_FRAGMENT_EMAIL_ADDRESS:
            case WHICH_FRAGMENT_POSTAL_ADDRESS:
                return false;

            default:
                // Trap
                return true;
        }
    }

}

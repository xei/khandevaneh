package ir.tvnasim.khandevaneh.account.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.tvnasim.khandevaneh.R;



public class UserAvatarFragment extends Fragment {

    public UserAvatarFragment() {
        // Required empty public constructor
    }

    public static UserAvatarFragment newInstance() {
        UserAvatarFragment fragment = new UserAvatarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_avatar, container, false);

        Intent intent = new Intent();
        intent.setType("image/*");intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select immage"), 11);

        return view;
    }
}

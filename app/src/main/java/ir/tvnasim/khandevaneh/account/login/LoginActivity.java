package ir.tvnasim.khandevaneh.account.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.app.BaseActivity;

public class LoginActivity extends BaseActivity implements PhoneNoFragment.OnFragmentInteractionListener {
    public static void start(Context starter) {
        Intent intent = new Intent(starter, LoginActivity.class);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().add(R.id.activityLogin_frameLayout_fragmentContainer, PhoneNoFragment.newInstance()).commit();

    }

    @Override
    public int getToolbarViewId() {
        return R.layout.toolbar_login;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

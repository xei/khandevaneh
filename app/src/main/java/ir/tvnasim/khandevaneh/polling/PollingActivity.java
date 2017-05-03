package ir.tvnasim.khandevaneh.polling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.webapi.MockApiRequest;
import ir.tvnasim.khandevaneh.helper.webapi.WebApiHelper;

public class PollingActivity extends AppCompatActivity {

    private static final String KEY_EXTRA_POLLING_ID = "KEY_EXTRA_POLLING_ID";

    private String mPollingId;

    public static void start(Context starter, String pollingId) {
        Intent intent = new Intent(starter, PollingActivity.class);
        intent.putExtra(KEY_EXTRA_POLLING_ID, pollingId);
        starter.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        this.mPollingId = getIntent().getStringExtra(KEY_EXTRA_POLLING_ID);

        fetchPollingFromApi();
    }

    private void fetchPollingFromApi() {
        WebApiHelper.getPollingItem(mPollingId, "requestTag_pollingActivity_getPollingItem", new MockApiRequest.WebApiListener<PollingItem>() {
            @Override
            public void onResponse(PollingItem pollingItem) {
                Toast.makeText(PollingActivity.this, pollingItem.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorResponse(String errorMessage) {

            }
        }, null).send();
    }
}

package ir.tvnasim.khandevaneh.polling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/11/17.
 */

class PollingStatisticsListAdapter extends BaseAdapter{

    private ArrayList<PollingStatisticsItem> mPollingStatisticsItems;
    private long mMaxFrequency;

    public PollingStatisticsListAdapter(ArrayList<PollingStatisticsItem> mPollingStatisticsItems) {
        this.mPollingStatisticsItems = mPollingStatisticsItems;
    }

    @Override
    public int getCount() {
        return mPollingStatisticsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mPollingStatisticsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_statistics, parent,false);

        XeiTextView valueTextView = (XeiTextView) itemView.findViewById(R.id.rowPollingStatistics_xeiTextView_value);
        valueTextView.setText(HelperFunctions.persianizeDigitsInString(mPollingStatisticsItems.get(position).getValue()));

        XeiTextView frequencyTextView = (XeiTextView) itemView.findViewById(R.id.rowPollingStatistics_xeiTextView_frequency);
        frequencyTextView.setText(HelperFunctions.persianizeDigitsInString(String.valueOf(mPollingStatisticsItems.get(position).getFrequency())));
        setupBarChart(frequencyTextView, mPollingStatisticsItems.get(position).getFrequency());

        if (position == 0) {
            LinearLayout ll = (LinearLayout) itemView.findViewById(R.id.rowPollingStatistics_linearLayout_itemView);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll.getLayoutParams();
            lp.setMargins(0, HelperFunctions.dpToPx(parent.getContext(), 32), 0, 0);
        }

        if (position == mPollingStatisticsItems.size() - 1) {
            LinearLayout ll = (LinearLayout) itemView.findViewById(R.id.rowPollingStatistics_linearLayout_itemView);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll.getLayoutParams();
            lp.setMargins(0, 0, 0, HelperFunctions.dpToPx(parent.getContext(), 32));
        }

        return itemView;
    }

    private void setupBarChart(TextView frequencyTextView, double frequency) {
        float weight = (float) (frequency / mMaxFrequency);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                weight
        );
        frequencyTextView.setLayoutParams(param);
    }

    @Override
    public void notifyDataSetChanged() {
        mMaxFrequency = 0;
        for (PollingStatisticsItem item : mPollingStatisticsItems) {
            if (item.getFrequency() > mMaxFrequency) {
                mMaxFrequency = item.getFrequency();
            }
        }

        super.notifyDataSetChanged();
    }
}

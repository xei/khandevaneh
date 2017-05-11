package ir.tvnasim.khandevaneh.polling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.helper.HelperFunctions;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 5/11/17.
 */

class PollingStatisticsListAdapter extends BaseAdapter{

    private ArrayList<PollingStatisticsItem> mPollingStatisticsItems;

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

        return itemView;
    }
}

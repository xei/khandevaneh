package ir.tvnasim.khandevaneh.polling;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
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

/**
 * Created by hamidreza on 5/3/17.
 */

public class OptionsListAdapter extends BaseAdapter {

    private ArrayList<PollingOption> mOptions;
    private OptionType mOptionsType;
    private ArrayList<String> mSelectedOptions;

    public OptionsListAdapter(ArrayList<PollingOption> options, OptionType optionsType, ArrayList<String> selectedOptions) {
        this.mOptions = options;
        this.mOptionsType = optionsType;
        this.mSelectedOptions = selectedOptions;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_polling_options, parent, false);

        TextView titleTextView = (TextView) itemView.findViewById(R.id.rowPollingOptions_xeiTextView_title);
        titleTextView.setText(mOptions.get(position).getTitle());

        RelativeLayout rowView = (RelativeLayout) itemView.findViewById(R.id.rowPoling_relativeLayout_itemViewForMargin);

        GradientDrawable background = (GradientDrawable) rowView.getBackground();
        if (mSelectedOptions.contains(mOptions.get(position).getId())) {
            background.setColor(Color.RED);
            titleTextView.setTextColor(Color.WHITE);
        } else {
            background.setColor(Color.WHITE);
            titleTextView.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.textColor_dark));
        }

        if (position == 0) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rowView.getLayoutParams();
            lp.setMargins(HelperFunctions.dpToPx(parent.getContext(), 56), HelperFunctions.dpToPx(parent.getContext(), 16), HelperFunctions.dpToPx(parent.getContext(), 56), 0);
        }else if (position == mOptions.size() - 1) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rowView.getLayoutParams();
            lp.setMargins(HelperFunctions.dpToPx(parent.getContext(), 56), 0, HelperFunctions.dpToPx(parent.getContext(), 56), HelperFunctions.dpToPx(parent.getContext(), 16));
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rowView.getLayoutParams();
            lp.setMargins(HelperFunctions.dpToPx(parent.getContext(), 56), 0, HelperFunctions.dpToPx(parent.getContext(), 56), 0);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnOption(mOptions.get(position).getId());
            }
        });

        return itemView;
    }

    private void chooseAnOption(String optionId) {
        if (mOptionsType.getType() == OptionType.TYPE_OPTION_SINGLE) {
            mSelectedOptions.clear();
            mSelectedOptions.add(optionId);
        } else if (mOptionsType.getType() == OptionType.TYPE_OPTION_MULTI) {
            int index = mSelectedOptions.indexOf(optionId);
            if(index == -1) {
                mSelectedOptions.add(optionId);
            } else {
                mSelectedOptions.remove(index);
            }
        } else {
            // trap
        }
        notifyDataSetChanged();
    }

}

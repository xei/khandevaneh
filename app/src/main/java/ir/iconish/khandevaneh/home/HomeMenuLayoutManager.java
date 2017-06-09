package ir.iconish.khandevaneh.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by hamidreza on 4/28/17.
 */

public class HomeMenuLayoutManager extends GridLayoutManager {

    public HomeMenuLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public HomeMenuLayoutManager(Context context, HomeMenuAdapter adapter) {
        super(context, 2);
        init(adapter);
    }

    public HomeMenuLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    private void init(final HomeMenuAdapter adapter) {
        setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize;
                switch (adapter.getItemViewType(position)) {
                    case HomeMenuAdapter.TYPE_VIEW_SLIDER:
                        spanSize = 2;
                        break;

                    case HomeMenuAdapter.TYPE_VIEW_MENU_ITEM:
                        spanSize = 1;
                        break;

                    default:
                        spanSize = 0;
                }
                return spanSize;
            }
        });
    }

}

package ir.tvnasim.khandevaneh.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import ir.tvnasim.khandevaneh.R;
import ir.tvnasim.khandevaneh.account.User;
import ir.tvnasim.khandevaneh.archive.ArchiveActivity;
import ir.tvnasim.khandevaneh.helper.LogHelper;
import ir.tvnasim.khandevaneh.helper.imageloading.FrescoHelper;
import ir.tvnasim.khandevaneh.livelike.LiveLikeActivity;
import ir.tvnasim.khandevaneh.polling.PollingActivity;
import ir.tvnasim.khandevaneh.view.XeiTextView;

/**
 * Created by hamidreza on 4/20/17.
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.HomeMenuItemViewHolder> implements View.OnClickListener{

    private static final String TAG_DEBUG = HomeMenuAdapter.class.getSimpleName();

    private static final int SIZE_MENU = 6;

    private ArrayList<HomeMenuItem> menuItems = new ArrayList<>();

    public HomeMenuAdapter() {

        //Archive
        HomeMenuItem archive = new HomeMenuItem();
        archive.setId(HomeMenuItem.ID_ARCHIVE);
        archive.setTitle("آرشیو");
        archive.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(archive);

        // Live Like
        HomeMenuItem liveLive = new HomeMenuItem();
        liveLive.setId(HomeMenuItem.ID_LIVE_LIKE);
        liveLive.setTitle("لایک زنده");
        liveLive.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(liveLive);

        // Polling
        HomeMenuItem  polling = new HomeMenuItem();
        polling.setId(HomeMenuItem.ID_POLLING);
        polling.setTitle("نظرسنجی");
        polling.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(polling);

        // Competition
        HomeMenuItem competition = new HomeMenuItem();
        competition.setId(HomeMenuItem.ID_COMPETITION);
        competition.setTitle("مسابقه");
        competition.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(competition);

        // Awards
        HomeMenuItem awards = new HomeMenuItem();
        awards.setId(HomeMenuItem.ID_AWARDS);
        awards.setTitle("جایزه ها");
        awards.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(awards);

        // Campaign
        HomeMenuItem campaign = new HomeMenuItem();
        campaign.setId(HomeMenuItem.ID_CAMPAIGN);
        campaign.setTitle("کمپین");
        campaign.setBackgroundImage(User.getInstance().getAvatar());
        menuItems.add(campaign);

    }

    @Override
    public HomeMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_home, parent, false);
        return new HomeMenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeMenuItemViewHolder holder, int position) {
        HomeMenuItem item = menuItems.get(position);
        FrescoHelper.setImageUrl(holder.backgroundImage, item.getBackgroundImage());
        holder.title.setText(item.getTitle());
        holder.itemView.setTag(item.getId());
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return SIZE_MENU;
    }

    @Override
    public void onClick(View clickedView) {
        String id = (String) clickedView.getTag();
        Context context = clickedView.getContext();
        switch (id) {
            case HomeMenuItem.ID_LIVE_LIKE:
                LiveLikeActivity.start(context);
                break;
            case HomeMenuItem.ID_ARCHIVE:
                ArchiveActivity.start(context);
                break;
            case HomeMenuItem.ID_POLLING:
                PollingActivity.start(context);
                break;
            case HomeMenuItem.ID_COMPETITION:
                PollingActivity.start(context);
                break;
            case HomeMenuItem.ID_CAMPAIGN:
                Toast.makeText(context, context.getString(R.string.inform_notImplemented), Toast.LENGTH_SHORT).show();
                break;
            case HomeMenuItem.ID_AWARDS:
                Toast.makeText(context, context.getString(R.string.inform_notImplemented), Toast.LENGTH_SHORT).show();
                break;
            default:
                LogHelper.logError(TAG_DEBUG, "invalid item id!");

        }
    }

    class HomeMenuItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        SimpleDraweeView backgroundImage;
        XeiTextView title;

        public HomeMenuItemViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            backgroundImage = (SimpleDraweeView) itemView.findViewById(R.id.rowMenuHome_simpleDraweeView_image);
            title = (XeiTextView) itemView.findViewById(R.id.rowMenuHome_xeiTextView_title);
        }
    }

}

package ir.tvnasim.khandevaneh.home;

/**
 * Created by hamidreza on 4/20/17.
 */

class HomeMenuItem {

    static final String ID_LIVE_LIKE = "ID_LIVE_LIKE";
    static final String ID_ARCHIVE = "ID_ARCHIVE";
    static final String ID_POLLING = "ID_POLLING";
    static final String ID_COMPETITION = "ID_COMPETITION";
    static final String ID_CAMPAIGN = "ID_CAMPAIGN";
    static final String ID_AWARDS = "ID_AWARDS";

    private String id;
    private String title;
    private int backgroundImageResourceId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackgroundImageResourceId() {
        return backgroundImageResourceId;
    }

    public void setBackgroundImageResourceId(int backgroundImageResourceId) {
        this.backgroundImageResourceId = backgroundImageResourceId;
    }
}

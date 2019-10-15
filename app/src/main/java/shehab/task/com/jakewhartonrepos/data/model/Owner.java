
package shehab.task.com.jakewhartonrepos.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("repos_url")
    @Expose
    public String repos_url;

    public String getRepos_url() {
        return repos_url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

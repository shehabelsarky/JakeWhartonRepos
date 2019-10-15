
package shehab.task.com.jakewhartonrepos.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ReposResponse {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("owner")
    @Expose
    @Embedded
    public Owner owner;
    public int type = 1;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}

package model.manager;

import java.io.Serializable;
import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class VideoResponse implements Serializable {

    @SerializedName("remain")
    private int remain;

    @SerializedName("undone")
    private boolean undone;

    @SerializedName("data")
    private List<Video> data;

    public VideoResponse(int remain, boolean undone, List<Video> data) {
        this.remain = remain;
        this.undone = undone;
        this.data = data;
    }

    public int getRemains() {
        return remain;
    }

    public void setRemains(int remains) {
        this.remain = remains;
    }

    public boolean getUndone() {
        return undone;
    }

    public void setUndone(boolean undone) {
        this.undone = undone;
    }

    public List<Video> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "VideoResponse{" +
                "remains=" + remain +
                ", undone=" + undone +
                ", data=" + data.size() +
                '}';
    }

    public void setData(List<Video> data) {
        this.data = data;
    }



}
package at.fhooe.mc.todolist.model;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Represents a single task.
 */
@Entity
public class Task implements Serializable {

    //primary key for the database
    @PrimaryKey(autoGenerate = true)
    private int mId;

    //ttask title
    @ColumnInfo(name = "task")
    private String mTask;

    //task description
    @ColumnInfo(name = "desc")
    private String mDesc;

    //true if task is finished
    @ColumnInfo(name = "finished")
    private boolean mFinished;

    //image
    @ColumnInfo(name = "image")
    private String mImage;

    /**
     * Getter of the primary key.
     *
     * @return the primary key
     */
    public int getId() {
        return mId;
    }

    /**
     * Setter of the primary key.
     */
    public void setId(int _id) {
        this.mId = _id;
    }

    /**
     * Getter of the task title.
     *
     * @return the task title
     */
    public String getTask() {
        return mTask;
    }

    /**
     * Setter of the task title.
     */
    public void setTask(String _task) {
        this.mTask = _task;
    }

    /**
     * Getter of the task description.
     *
     * @return the task description
     */
    public String getDesc() {
        return mDesc;
    }

    /**
     * Setter of the task description.
     */
    public void setDesc(String _desc) {
        this.mDesc = _desc;
    }

    /**
     * Is true, if the task is finished.
     *
     * @return whether it is finished or not
     */
    public boolean isFinished() {
        return mFinished;
    }

    /**
     * Sets true, if the task is finished.
     */
    public void setFinished(boolean _finished) {
        this.mFinished = _finished;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String _imageUrl) {
        this.mImage = _imageUrl;
    }
}


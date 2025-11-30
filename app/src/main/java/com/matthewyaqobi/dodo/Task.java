package com.matthewyaqobi.dodo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_task")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean completed;
    @ColumnInfo(name = "task_title")
    private String taskTitle;
    private long date;
    private int time;
    private int stickerRes;
    private String bgColorHex;

    public String getBgColorHex() {
        return bgColorHex;
    }

    public void setBgColorHex(String bgColorHex) {
        this.bgColorHex = bgColorHex;
    }

    public int getStickerRes() {
        return stickerRes;
    }

    public void setStickerRes(int stickerRes) {
        this.stickerRes = stickerRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }


    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public Task() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
        dest.writeString(this.taskTitle);
        dest.writeLong(this.date);
        dest.writeInt(this.time);
        dest.writeInt(this.stickerRes);
        dest.writeString(this.bgColorHex);
    }

    protected Task(Parcel in) {
        this.id = in.readInt();
        this.completed = in.readByte() != 0;
        this.taskTitle = in.readString();
        this.date = in.readLong();
        this.time = in.readInt();
        this.stickerRes = in.readInt();
        this.bgColorHex = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}


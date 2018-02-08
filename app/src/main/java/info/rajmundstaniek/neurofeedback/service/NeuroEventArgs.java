package info.rajmundstaniek.neurofeedback.service;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajmu on 08.02.2018.
 */

public class NeuroEventArgs implements Parcelable {
    public static final String TAG = NeuroEventArgs.class.getSimpleName();

    public NeuroReceiverService.ACTION Action;
    public String Message;
    public Object Data;

    public NeuroEventArgs(NeuroReceiverService.ACTION action, String message, Object data) {
        Action = action;
        Message = message;
        Data = data;
    }

    protected NeuroEventArgs(Parcel in) {
        Message = in.readString();
    }

    public static final Creator<NeuroEventArgs> CREATOR = new Creator<NeuroEventArgs>() {
        @Override
        public NeuroEventArgs createFromParcel(Parcel in) {
            return new NeuroEventArgs(in);
        }

        @Override
        public NeuroEventArgs[] newArray(int size) {
            return new NeuroEventArgs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[]{Action, Message, Data});
    }
}

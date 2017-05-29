package com.salman.zach.emorating;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.salman.zach.emorater.EmoRatingView;
import com.salman.zach.emorater.IEmoRatingListener;

/**
 * Created by Zach on 5/5/2017.
 */

public class RatingDialog extends AlertDialog {

    TextView status;

    public RatingDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogTheme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setCancelable(false);
        setContentView(R.layout.rating_dialog);
        this.setCanceledOnTouchOutside(true);
        final EmoRatingView emoRatingView = (EmoRatingView) findViewById(R.id.rateBar);
        status = (TextView) findViewById(R.id.status);
        emoRatingView.setOnRatingSliderChangeListener(new IEmoRatingListener() {
            @Override
            public void onRatingFinal(int rating) {
                setStatusByRating(emoRatingView.getRating());
            }

            @Override
            public void onRatingCancel() {

            }

            @Override
            public void onRatingPending(int rating) {

            }
        });


    }


    private void setStatusByRating(int rating) {

        switch (rating) {
            case 1:
                status.setText("Desperate !");
                break;
            case 2:
                status.setText("Sceptic !");
                break;
            case 3:
                status.setText("Happy !");
                break;
            case 4:
                status.setText("Smiling !");
                break;
            case 5:
                status.setText("Loved !");
                break;
            default:
                status.setText("Status");
                break;
        }
    }
}

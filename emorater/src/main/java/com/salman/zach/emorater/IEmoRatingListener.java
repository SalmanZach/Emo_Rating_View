package com.salman.zach.emorater;

/**
 * Created by Zach on 5/27/2017.
 */

public interface IEmoRatingListener {


    /**
     * Notice when the user has selected a final rating.
     *
     * @param rating
     */
    void onRatingFinal(int rating);


    /**
     * Notice when the user has canceled the rating.
     */
    void onRatingCancel();


    /**
     * Notice when the user is being rated you.
     *
     * @param rating
     */
    void onRatingPending(int rating);


}

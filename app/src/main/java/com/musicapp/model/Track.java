package com.musicapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chetan on 10/12/18.
 */

public class Track implements Parcelable{
    private String artistName;
    private String collectionName;
    private String artworkUrl100;
    private String artworkUrl60;
    private String currency;
    private String releaseDate;
    private String trackName;
    private float trackPrice;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public float getTrackPrice() {
        return trackPrice;
    }

    public String getPrice(){
        return trackPrice + " " + currency;
    }

    public void setTrackPrice(float trackPrice) {
        this.trackPrice = trackPrice;
    }

    protected Track(Parcel in) {
        artistName = in.readString();
        collectionName = in.readString();
        artworkUrl100 = in.readString();
        artworkUrl60 = in.readString();
        currency = in.readString();
        releaseDate = in.readString();
        trackName = in.readString();
        trackPrice = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeString(artworkUrl100);
        dest.writeString(artworkUrl60);
        dest.writeString(currency);
        dest.writeString(releaseDate);
        dest.writeString(trackName);
        dest.writeFloat(trackPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}

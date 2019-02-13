package com.bootmovies.movies.domain.movie;

public class Tomato {
    private Integer meter;
    private String image;
    private Double rating;
    private Integer reviews;
    private Integer fresh;
    private String consensus;
    private Integer userMeter;
    private Integer userRating;

    public Integer getMeter() {
        return meter;
    }

    public void setMeter(Integer meter) {
        this.meter = meter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Integer getFresh() {
        return fresh;
    }

    public void setFresh(Integer fresh) {
        this.fresh = fresh;
    }

    public String getConsensus() {
        return consensus;
    }

    public void setConsensus(String consensus) {
        this.consensus = consensus;
    }

    public Integer getUserMeter() {
        return userMeter;
    }

    public void setUserMeter(Integer userMeter) {
        this.userMeter = userMeter;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Integer getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(Integer userReviews) {
        this.userReviews = userReviews;
    }

    private Integer userReviews;

}

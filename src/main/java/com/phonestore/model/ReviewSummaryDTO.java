package com.phonestore.model;

import java.util.HashMap;
import java.util.Map;

public class ReviewSummaryDTO {
    private double avgRating;
    private long totalReviews;

    private long count5;
    private long count4;
    private long count3;
    private long count2;
    private long count1;
    public double getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
    public long getTotalReviews() {
        return totalReviews;
    }
    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getCount5() { return count5; }
    public void setCount5(long count5) { this.count5 = count5; }

    public long getCount4() { return count4; }
    public void setCount4(long count4) { this.count4 = count4; }

    public long getCount3() { return count3; }
    public void setCount3(long count3) { this.count3 = count3; }

    public long getCount2() { return count2; }
    public void setCount2(long count2) { this.count2 = count2; }

    public long getCount1() { return count1; }
    public void setCount1(long count1) { this.count1 = count1; }
}
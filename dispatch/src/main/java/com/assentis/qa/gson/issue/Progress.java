package com.assentis.qa.gson.issue;

public class Progress {

    private Integer progress;
    private Integer total;
    private Integer percent;

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "progress=" + progress +
                ", total=" + total +
                ", percent=" + percent +
                '}';
    }
}

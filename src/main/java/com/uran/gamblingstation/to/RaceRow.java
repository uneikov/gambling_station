package com.uran.gamblingstation.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class RaceRow implements Serializable{
    private static final long serialVersionUID = 3L;

    private LocalDateTime start;
    private LocalDateTime finish;
    private List<String> en_names;
    private List<String> ru_names;
    private String en_winning;
    private String ru_winning;
    private int count;
    private Double stakeSum;
    private Double amountSum;

    public RaceRow() {
    }

    public RaceRow(
            LocalDateTime start,
            LocalDateTime finish,
            List<String> en_names,
            List<String> ru_names,
            String en_winning,
            String ru_winning,
            int count,
            Double stakeSum,
            Double amountSum) {
        this.start = start;
        this.finish = finish;
        this.en_names = en_names;
        this.ru_names = ru_names;
        this.en_winning = en_winning;
        this.ru_winning = ru_winning;
        this.count = count;
        this.stakeSum = stakeSum;
        this.amountSum = amountSum;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public List<String> getEn_names() {
        return en_names;
    }

    public void setEn_names(List<String> en_names) {
        this.en_names = en_names;
    }

    public List<String> getRu_names() {
        return ru_names;
    }

    public void setRu_names(List<String> ru_names) {
        this.ru_names = ru_names;
    }

    public String getEn_winning() {
        return en_winning;
    }

    public void setEn_winning(String en_winning) {
        this.en_winning = en_winning;
    }

    public String getRu_winning() {
        return ru_winning;
    }

    public void setRu_winning(String ru_winning) {
        this.ru_winning = ru_winning;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getStakeSum() {
        return stakeSum;
    }

    public void setStakeSum(Double stakeSum) {
        this.stakeSum = stakeSum;
    }

    public Double getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(Double amountSum) {
        this.amountSum = amountSum;
    }

    @Override
    public String toString() {
        return "RaceRow{" +
                "start=" + start +
                ", finish=" + finish +
                ", en_names=" + en_names +
                ", ru_names=" + ru_names +
                ", en_winning='" + en_winning + '\'' +
                ", ru_winning='" + ru_winning + '\'' +
                ", count=" + count +
                ", stakeSum=" + stakeSum +
                ", amountSum=" + amountSum +
                '}';
    }
}

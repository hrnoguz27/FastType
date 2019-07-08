package com.harun.fasttype;

public class Score {
    int score;
    String scoreDate;

    public Score(int score, String scoreDate) {
        this.score = score;
        this.scoreDate = scoreDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(String scoreDate) {
        this.scoreDate = scoreDate;
    }
}

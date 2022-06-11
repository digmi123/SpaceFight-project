package com.example.spacefight;

public class scoreModel implements Comparable {
    String playerName;
    int playerScore;

    public scoreModel(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public int compareTo(Object scoreToCompare) {
        int compareage = ((scoreModel)scoreToCompare).getPlayerScore();
        return compareage-this.playerScore;
    }
}

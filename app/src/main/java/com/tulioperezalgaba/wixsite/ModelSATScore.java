package com.tulioperezalgaba.wixsite;

public class ModelSATScore {
    private String dbn; // school code
    private int numTakers; // number of students who took the SAT
    private int criticalReadingMean;
    private int mathMean;
    private int writingMean;

    public ModelSATScore(String dbn, int numTakers, int criticalReadingMean, int mathMean, int writingMean) {
        this.dbn = dbn;
        this.numTakers = numTakers;
        this.criticalReadingMean = criticalReadingMean;
        this.mathMean = mathMean;
        this.writingMean = writingMean;
    }

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public int getNumTakers() {
        return numTakers;
    }

    public void setNumTakers(int numTakers) {
        this.numTakers = numTakers;
    }

    public int getCriticalReadingMean() {
        return criticalReadingMean;
    }

    public void setCriticalReadingMean(int criticalReadingMean) {
        this.criticalReadingMean = criticalReadingMean;
    }

    public int getMathMean() {
        return mathMean;
    }

    public void setMathMean(int mathMean) {
        this.mathMean = mathMean;
    }

    public int getWritingMean() {
        return writingMean;
    }

    public void setWritingMean(int writingMean) {
        this.writingMean = writingMean;
    }
}


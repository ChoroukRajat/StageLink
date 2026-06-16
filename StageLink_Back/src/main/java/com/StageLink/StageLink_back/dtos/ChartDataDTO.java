package com.StageLink.StageLink_back.dtos;

public class ChartDataDTO {
    private String label;  // Label for the chart
    private long value;    // Value corresponding to the label

    public ChartDataDTO(String label, long value) {
        this.label = label;
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}


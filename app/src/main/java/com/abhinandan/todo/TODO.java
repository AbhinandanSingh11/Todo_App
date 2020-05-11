package com.abhinandan.todo;

public class TODO {
    private String title;
    private String secondaryText;
    private String primaryText;
    private boolean isDone;
    private boolean highlighted;

    public TODO(String title, String secondaryText, String primaryText) {
        this.title = title;
        this.secondaryText = secondaryText;
        this.primaryText = primaryText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public void setPrimaryText(String primaryText) {
        this.primaryText = primaryText;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}

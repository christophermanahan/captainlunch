package io.github.christophermanahan.captainlunch.web.slack;

public class SlackMessage {

    private String text;

    SlackMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package io.github.christophermanahan.captainlunch.web.slack;

public class SlackChannelMessage {

    private String text;
    private String response_type;

    public SlackChannelMessage(String text) {
        this.text = text;
        this.response_type = "in_channel";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }
}



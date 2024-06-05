package com.iot.SmartDeviceMS;

import java.util.List;

public class ThingSpeakResponse {
    private Channel channel;
    private List<Feed> feeds;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }
}

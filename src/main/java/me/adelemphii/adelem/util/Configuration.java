package me.adelemphii.adelem.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {

    private Boolean debug;

    private Map<String, String> bot;

    private Map<String, String> api;

    private Map<String, String> credentials;

    private List<String> channels;

    private boolean discordBroadcast;

    private List<String> webhooks;

    private boolean chatLink;

    private String discordBotToken;

    private List<Long> specifyDiscordChannels;

    private List<String> twitchSimpleLockdownCommands;

    public List<String> getTwitchSimpleLockdownCommands() {
        return twitchSimpleLockdownCommands;
    }

    public void setTwitchSimpleLockdownCommands(List<String> twitchSimpleLockdownCommands) {
        this.twitchSimpleLockdownCommands = twitchSimpleLockdownCommands;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Map<String, String> getBot() {
        return bot;
    }

    public void setBot(Map<String, String> bot) {
        this.bot = bot;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public boolean getDiscordBroadcast() {
        return discordBroadcast;
    }

    public void setDiscordBroadcast(boolean broadcast) {
        this.discordBroadcast = broadcast;
    }

    public List<String> getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(List<String> webhooks) {
        this.webhooks = webhooks;
    }

    public boolean hasWebhooks() {
        return !webhooks.isEmpty();
    }

    public boolean getChatLink() {
        return chatLink;
    }

    public void setChatLink(boolean chatLink) {
        this.chatLink = chatLink;
    }

    public String getDiscordBotToken() {
        return discordBotToken;
    }

    public void setDiscordBotToken(String discordBotToken) {
        this.discordBotToken = discordBotToken;
    }

    public void setSpecifyDiscordChannels(List<Long> specifyDiscordChannels) {
        this.specifyDiscordChannels = specifyDiscordChannels;
    }

    public List<Long> getSpecifyDiscordChannels() {
        return specifyDiscordChannels;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "bot=" + bot +
                ", api=" + api +
                ", credentials=" + credentials +
                ", channels=" + channels +
                ", discord broadcast=" + discordBroadcast +
                ", has webhooks=" + hasWebhooks() +
                '}';
    }
}

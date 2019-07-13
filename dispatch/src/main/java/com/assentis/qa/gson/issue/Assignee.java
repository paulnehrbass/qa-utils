
package com.assentis.qa.gson.issue;


public class Assignee {

    private String self;
    private String name;
    private String shortcut;
    private String key;
    private String emailAddress;
    private AvatarUrls_ avatarUrls;
    private String displayName;
    private Boolean active;
    private String timeZone;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public AvatarUrls_ getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(AvatarUrls_ avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	@Override
	public String toString() {
		return "Assignee [self=" + self + ", name=" + name + ", key=" + key + ", emailAddress=" + emailAddress
				+ ", avatarUrls=" + avatarUrls + ", displayName=" + displayName + ", active=" + active + ", timeZone="
				+ timeZone + "]";
	}

    
}

package com.example.event.booking.reftype;

public enum YNStatus {

    YES("T", "Yes"),
    NO("F", "No");

    private String status;
    private String display;

    YNStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static YNStatus statusMatch(String status) {

        for (YNStatus ynStatus : YNStatus.values()) {
            if (ynStatus.getStatus().equalsIgnoreCase(status)) {
                return ynStatus;
            }
        }
        return YES;
    }
}

package ua.softserve.rv_028.issuecitymonitor.entity.enums;

public enum PdfTypes {
    ISSUES,
    EVENTS,
    PETITIONS,
    USERS;

    public String getName() {
        return this.name().toLowerCase();
    }


}
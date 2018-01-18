package ua.softserve.rv_028.issuecitymonitor.controller.PDF;


public class DataObject {

    private String noteID;
    private String title;
    private String desc;
    private String cat;
    private String userID;
    private String date;

    public String getNoteID() {
        return noteID;
    }
    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCat() {
        return cat;
    }
    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}

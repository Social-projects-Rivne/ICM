package ua.softserve.rv_028.issuecitymonitor.controller.PDF;


public class DataObject {

    private String noteID;
    private String title;
    private String desc;
    private String cat;
    private String userID;
    private String date;
    private String regDate;
    private String email;
    private String phone;
    private String role;

    public DataObject(PdfWritable writable) {
        this.setNoteID(Long.toString(writable.getId()));
        this.setTitle(writable.getTitle());
        this.setDesc(writable.getDescription());
        this.setCat(writable.getCat());

        if (writable.getUserDto() != null) this.setUserID(Long.toString(writable.getUserDto().getId()));
        else this.setUserID((null));

        if (writable.getInitialDate() != null) this.setDate(writable.getInitialDate().toString());
        else this.setDate((null));

        this.setPhone(writable.getPhone());
        this.setEmail(writable.getMail());

        if (writable.getRegDate() != null) this.setRegDate(writable.getRegDate().toString());
        else this.setRegDate((null));

        if (writable.getRole() != null) this.setRole(writable.getRole().toString());
        else this.setRole((null));

    }

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

    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

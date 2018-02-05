package ua.softserve.rv_028.issuecitymonitor.controller.PDF;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataObject {

    String noteID;
    String title;
    String desc;
    String cat;
    String userID;
    String date;
    String regDate;
    String email;
    String phone;
    String role;

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
}

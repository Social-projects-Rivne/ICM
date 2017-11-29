package ua.softserve.rv_028.issuecitymonitor.entity;

import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "petitions")
public class Petition{

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "initial_date")
    private String initialDate;

    @Column(name = "category")
    private PetitionCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionAttachment.class)
    private Set<PetitionAttachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionChangeRecord.class)
    private Set<PetitionChangeRecord> changeRecords = new HashSet<>();

    public Petition() {
    }

    public Petition(User user, String title, String description, String initialDate, PetitionCategory category) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public PetitionCategory getCategory() {
        return category;
    }

    public void setCategory(PetitionCategory category) {
        this.category = category;
    }

    public Set<PetitionAttachment> getAttachments() {
        return attachments;
    }

    public Set<PetitionChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Petition)) return false;

        Petition petition = (Petition) o;

        if (id != petition.id) return false;
        if (user != null ? !user.equals(petition.user) : petition.user != null) return false;
        if (title != null ? !title.equals(petition.title) : petition.title != null) return false;
        if (description != null ? !description.equals(petition.description) : petition.description != null)
            return false;
        if (initialDate != null ? !initialDate.equals(petition.initialDate) : petition.initialDate != null)
            return false;
        return category == petition.category;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (initialDate != null ? initialDate.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Petition{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", category=" + category +
                '}';
    }
}

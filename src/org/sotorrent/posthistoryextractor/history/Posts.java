package org.sotorrent.posthistoryextractor.history;

import javax.persistence.*;

@Entity
@Table(name="Posts")
public class Posts {
    public static final byte UNKNOWN_ID = 0;
    public static final byte QUESTION_ID = 1;
    public static final byte ANSWER_ID = 2;

    private int Id;
    private int postTypeId;
    private String tags;

    public Posts() {}

    @Id
    @Column(name = "Id")
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Basic
    @Column(name = "PostTypeId")
    public int getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(int postTypeId) {
        this.postTypeId = postTypeId;
    }

    @Basic
    @Column(name = "Tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

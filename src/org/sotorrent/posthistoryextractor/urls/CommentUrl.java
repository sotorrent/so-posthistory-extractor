package org.sotorrent.posthistoryextractor.urls;

import javax.persistence.*;

@Entity
@Table(name="CommentUrl")
public class CommentUrl{
    private int id;
    private Integer postId;
    private Integer commentId;
    private String linkType;
    private String linkPosition;
    private String linkAnchor;
    private String protocol;
    private String rootDomain;
    private String completeDomain;
    private String path;
    private String fragmentIdentifier;
    private String url;

    public CommentUrl(){
        this.commentId = null;
        this.linkType = null;
        this.linkPosition = null;
        this.linkAnchor = null;
        this.protocol = null;
        this.rootDomain = null;
        this.completeDomain = null;
        this.path = null;
        this.fragmentIdentifier = null;
        this.url = null;
    }

    public CommentUrl(int postId, int commentId, Link link, String markdownContent){
        this.postId = postId;
        this.commentId = commentId;
        this.linkType = link.getType();
        this.linkPosition = link.getPosition(markdownContent);
        this.linkAnchor = link.getAnchor();
        this.protocol = link.getProtocol();
        this.rootDomain = link.getRootDomain();
        this.completeDomain = link.getCompleteDomain();
        this.path = link.getPath();
        this.fragmentIdentifier = link.getFragmentIdentifier();
        this.url = link.getUrl();
    }

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PostId")
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "CommentId")
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "LinkType")
    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @Basic
    @Column(name = "LinkPosition")
    public String getLinkPosition() {
        return linkPosition;
    }

    public void setLinkPosition(String linkPosition) {
        this.linkPosition = linkPosition;
    }

    @Basic
    @Column(name = "LinkAnchor")
    public String getLinkAnchor() {
        return linkAnchor;
    }

    public void setLinkAnchor(String linkAnchor) {
        this.linkAnchor = linkAnchor;
    }

    @Basic
    @Column(name = "Protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "RootDomain")
    public String getRootDomain() {
        return rootDomain;
    }

    public void setRootDomain(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    @Basic
    @Column(name = "CompleteDomain")
    public String getCompleteDomain() {
        return completeDomain;
    }

    public void setCompleteDomain(String completeDomain) {
        this.completeDomain = completeDomain;
    }

    @Basic
    @Column(name = "Path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "FragmentIdentifier")
    public String getFragmentIdentifier() {
        return fragmentIdentifier;
    }

    public void setFragmentIdentifier(String fragmentIdentifier) {
        this.fragmentIdentifier = fragmentIdentifier;
    }

    @Basic
    @Column(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Url: " + url + "\n";
    }
}
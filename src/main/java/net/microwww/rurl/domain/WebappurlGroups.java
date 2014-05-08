package net.microwww.rurl.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import net.microwww.common.BeanContextFactory;
import net.microwww.rurl.repository.WebappurlGroupsRepository;

/**
 * Auto-generated by: org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
@Entity
@Table(name = "webapp_url_groups")
public class WebappurlGroups implements Serializable {

    public static List<Webapp> listWebapp(Groups gp) {
        return getRepository().findWebappByGroupsId(gp.getId());
    }

    public static List<Groups> listGroups(Webapp app) {
        return getRepository().findGroupsByWebapp(app.getId());
    }

    public static WebappurlGroups getByGroupIdWebappurlId(int gid, int uid) {
        return getRepository().getByGroupsIdWebappurlId(gid, uid);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "groups_id")
    private Groups groups;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "webapp_id")
    private Webapp webapp;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "webapp_url_id")
    private Webappurl webappurl;

    public WebappurlGroups() {
    }

    public WebappurlGroups(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public Webappurl getWebappurl() {
        return webappurl;
    }

    public void setWebappurl(Webappurl webappurl) {
        this.webappurl = webappurl;
        if (webappurl != null) {
            this.webapp = webappurl.getWebapp();
        }
    }

    public Webapp getWebapp() {
        return this.webapp;
    }

    protected static WebappurlGroupsRepository getRepository() {
        return BeanContextFactory.get().getBean(WebappurlGroupsRepository.class);
    }

    public static WebappurlGroups getById(int id) {
        return getRepository().getById(id);
    }
}
package net.microwww.rurl.domain;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import net.microwww.common.BeanContextFactory;
import net.microwww.rurl.repository.WebappRepository;

/**
 * Auto-generated by: org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
@Entity
@Table(name = "webapp")
public class Webapp implements Serializable {

    public static List<Webapp> listWebapps() {
        return getRepository().findAll();
    }

    public static Webapp getByAppName(String appname) {
        return getRepository().getByAppName(appname);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @Basic
    @Column(name = "all_login", columnDefinition = "INT UNSIGNED")
    private boolean allLogin;

    @Basic
    @Column(name = "app_name", nullable = false)
    private String appName;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Basic
    @Column(length = 1000)
    private String discrption;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "login_group")
    private Groups loginGroup;

    @Basic
    private String name;

    @Basic
    @Column(name = "unknown_url", columnDefinition = "INT UNSIGNED")
    private boolean unknownUrl;

    @Basic
    @Column(name = "update_time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updateTime;

    public Webapp() {
    }

    public Webapp(int id) {
        this.id = id;
    }

    public boolean getAllLogin() {
        return allLogin;
    }

    public void setAllLogin(boolean allLogin) {
        this.allLogin = allLogin;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDiscrption() {
        return discrption;
    }

    public void setDiscrption(String discrption) {
        this.discrption = discrption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getUnknownUrl() {
        return unknownUrl;
    }

    public void setUnknownUrl(boolean unknownUrl) {
        this.unknownUrl = unknownUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Groups getLoginGroup() {
        return loginGroup;
    }

    public void setLoginGroup(Groups loginGroup) {
        this.loginGroup = loginGroup;
    }

    protected static WebappRepository getRepository() {
        return BeanContextFactory.get().getBean(WebappRepository.class);
    }

    public static Webapp getById(int id) {
        return getRepository().getById(id);
    }

    public boolean hasLoginRight(String account) {
        Account acc = Account.getByAccount(account);
        if (this.getLoginGroup() == null) {
            return this.allLogin;
        }
        AccountGroups accountGroups = AccountGroups.getByAccountIdGroupsId(acc.getId(), this.getLoginGroup().getId());
        if (this.allLogin) {
            return null == accountGroups;
        }
        return null != accountGroups;
    }

    public List<Webappurl> listAccountRight(String account) {
        Account acc = Account.getByAccount(account);
        if(acc == null){
            return Collections.EMPTY_LIST;
        }
        return getRepository().findRightByAccountIdWebappId(acc.getId(), this.getId());
    }
}

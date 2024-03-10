package com.ayushsingh.cacmp_backend.util.emailUtil;

import java.util.HashMap;
import java.util.Map;



public abstract class AbstractEmailContext {

    private String from;
    private String to;
    private String subject;
    private String email;
    private String attachment;
    private String fromDisplayName;
    private String emailLanguage;
    private String displayName;
    private String templateLocation;
    private Map<String, Object> context;

    public AbstractEmailContext(){
        this.context=new HashMap<>();
    }

    public void init(){
        //we can do any common configuration setup here
        //like setting up some base URL and context
    }

    public Object put(String key, Object value) {
        return key ==null ? null : this.context.put(key.intern(),value);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getFromDisplayName() {
        return fromDisplayName;
    }

    public void setFromDisplayName(String fromDisplayName) {
        this.fromDisplayName = fromDisplayName;
    }

    public String getEmailLanguage() {
        return emailLanguage;
    }

    public void setEmailLanguage(String emailLanguage) {
        this.emailLanguage = emailLanguage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }


    @Override
    public String toString() {
        return "AbstractEmailContext{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", email='" + email + '\'' +
                ", attachment='" + attachment + '\'' +
                ", fromDisplayName='" + fromDisplayName + '\'' +
                ", emailLanguage='" + emailLanguage + '\'' +
                ", displayName='" + displayName + '\'' +
                ", templateLocation='" + templateLocation + '\'' +
                ", context=" + context +
                '}';
    }
}

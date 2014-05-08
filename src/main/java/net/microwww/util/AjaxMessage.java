/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author changshu.li
 */
public class AjaxMessage {

    private final List<String> error = new ArrayList<String>();
    private final List<String> msg = new ArrayList<String>();
    private Object data;

    public boolean isSuccess() {
        return !hasError();
    }

    public boolean hasError() {
        return !error.isEmpty();
    }

    public void addError(String message) {
        error.add(message);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(error);
    }

    public String getErrors(int index) {
        return error.get(index);
    }

    public void clearErrors() {
        error.clear();
    }

    public void clearError(int index) {
        error.remove(index);
    }

    public boolean hasMessage() {
        return msg.size() > 0;
    }

    public void addMessage(String message) {
        msg.add(message);
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(msg);
    }

    public String getMessages(int index) {
        return msg.get(index);
    }

    public void clearMessages() {
        msg.clear();
    }

    public void clearMessage(int index) {
        msg.remove(index);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.chengzi.shiro.spring.shiroconfig.subject;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:22
 */
public class CustomSubject extends DefaultSubjectDAO {
    @Override
    public void setSessionStorageEvaluator(SessionStorageEvaluator sessionStorageEvaluator) {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        this.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    }
}

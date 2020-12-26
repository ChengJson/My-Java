package com.fantaike.template.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @ClassName StatelessDefaultSubjectFactory
 * @Description 自定义无状态shiro subect工厂 目的是使用token验真 并禁用session
 * @Author wugz
 * @Date 2019/7/13 16:10
 * @Version 1.0
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}

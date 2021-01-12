package com.chengzi.shiro.spring.shiroconfig.session;

import com.chengzi.shiro.spring.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/23 15:15
 */
public class RedisSessionDao extends AbstractSessionDAO {
    private static Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);
    @Autowired
    private JedisUtil jedisUtil;

    private final static String SHIRO_SESSION_PREFIX = "SHIRO_SESSION:";
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    private void saveSession(Session session) {
        if (session !=null && session.getId() != null) {
            byte[] key = getkey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
        }
    }

    private byte[] getkey(String key) {
        return  (SHIRO_SESSION_PREFIX + key).getBytes();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) return null;
        byte[] key = getkey(sessionId.toString());
        byte[] bytes = jedisUtil.get(key);
        LOG.info("====redis====");
        Session session = (Session) SerializationUtils.deserialize(bytes);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        String sessionId = session.getId().toString();
        byte[] key = getkey(sessionId.toString());
        jedisUtil.delete(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        for( byte[] key : keys ) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }
}

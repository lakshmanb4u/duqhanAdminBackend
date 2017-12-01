/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.DuqhanAdminDao;
import com.weavers.duqhan.domain.DuqhanAdmin;
import java.util.Date;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class DuqhanAdminDaoJpa extends BaseDaoJpa<DuqhanAdmin> implements DuqhanAdminDao {

    public DuqhanAdminDaoJpa() {
        super(DuqhanAdmin.class, "DuqhanAdmin");
    }

    @Override
    public DuqhanAdmin getAdminByEmailAndPassword(String email, String password) {
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM DuqhanAdmin AS a WHERE a.email=:email AND a.password=:password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            return (DuqhanAdmin) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public DuqhanAdmin getTokenByMail(String email) {
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM DuqhanAdmin a WHERE a.email=:email");
            query.setParameter("email", email);
            return (DuqhanAdmin) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public DuqhanAdmin getTokenByMailAndUserId(String email, Long userId) {
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM DuqhanAdmin a WHERE a.email=:email AND a.id=:userId");
            query.setParameter("email", email);
            query.setParameter("userId", userId);
            return (DuqhanAdmin) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public DuqhanAdmin getTokenByEmailAndToken(String email, String aouthToken) {
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM DuqhanAdmin a WHERE a.email=:email AND a.aouthToken=:aouthToken");
            query.setParameter("email", email);
            query.setParameter("aouthToken", aouthToken);
            return (DuqhanAdmin) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException nre) {
            return null;
        }
    }

    @Override
    public DuqhanAdmin getUserIdByTokenIfValid(String aouthToken) {
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM DuqhanAdmin a WHERE a.aouthToken=:aouthToken AND a.validTill>:thisTime");
            query.setParameter("thisTime", new Date());
            query.setParameter("aouthToken", aouthToken);
            return (DuqhanAdmin) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException nre) {
            return null;
        }
    }

}

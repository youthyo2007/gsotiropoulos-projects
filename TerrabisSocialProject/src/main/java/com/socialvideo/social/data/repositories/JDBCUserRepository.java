package com.socialvideo.social.data.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.socialvideo.social.data.dto.SocialProfileDTO;
import com.socialvideo.social.data.dto.User;



@Repository
public class JDBCUserRepository  {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init() {
//        org.hsqldb.util.DatabaseManagerSwing.main(new String[]{"--url", "jdbc:hsqldb:mem:myDb", "--noexit"});
    }

    private static final String QUERY_USERS =
            " SELECT up.userId, up.email, up.firstName, up.lastName, up.name,  up.username, " +
                    " up.imageUrl, au.authority, uc.providerId, uc.profileUrl " +
                    " FROM UserProfile up " +
                    " LEFT OUTER JOIN UserConnection uc ON uc.userId = up.userId " +
                    " LEFT OUTER JOIN authorities au ON au.username = up.userId ";

    private static final String QUERY_USER_BY_USERID = QUERY_USERS + " WHERE up.userId = ? ";

    

    public User findByUserName(String username) {
        List<User> results = jdbcTemplate.query(QUERY_USER_BY_USERID, new Object[]{username}, new UserResultSetExtractor());
        return DataAccessUtils.requiredSingleResult(results);
    }


    public List<User> findAll() {
        return jdbcTemplate.query(QUERY_USERS, new UserResultSetExtractor());
    }



    public void createUser(String userId, SocialProfileDTO profile) {
        jdbcTemplate.update("INSERT into users(username,password,enabled) values(?,?,true)", userId, RandomStringUtils.randomAlphanumeric(8));
        jdbcTemplate.update("INSERT into authorities(username,authority) values(?,?)", userId, "user");
        jdbcTemplate.update("INSERT into UserProfile(userId, email, firstName, lastName, name, username, imageUrl) values(?,?,?,?,?,?,?)",
                userId,
                profile.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getName(),
                profile.getUsername(),
                profile.getImageUrl());
    }


    protected class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

        private HashMap<String, User> users = new HashMap<>();

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
                String userId = rs.getString("userId");
                User user = users.get(userId);
                if (user == null) {
                    user = mapRow(rs);
                    users.put(userId, user);
                }
                user.getAuthorities().add(rs.getString("authority"));
            }
            //return new ArrayList()<>(users.values());
            
            
            return null;
        }

        private User mapRow(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getString("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setFamilyName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setImageUrl(rs.getString("imageUrl"));
            user.setProfileUrl(rs.getString("profileUrl"));
            user.setProviderId(rs.getString("providerId"));
            return user;
        }


    }


}
package com.hmelocosta.app.dao;

import com.hmelocosta.app.model.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import static org.jongo.Oid.withOid;

public class UserRepoImp implements Repository<User> {
    private MongoCollection userCollection;

    public UserRepoImp(MongoCollection userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public List<User> fetchAll() {
        MongoCursor<User> all = userCollection.find().sort("(username : 1)").as(User.class);
        List<User> users = new ArrayList<>();
        while(all.hasNext()) {
            users.add(all.next());
        }
        return users;
    }

    @Override
    public User fetchOne(String id) {
        if(id == null) {
            return null;
        }
        return userCollection.findOne(withOid(id)).as(User.class);
    }

    @Override
    public void upsert(User object) {
        userCollection.save(object);
    }

    @Override
    public void delete(String id) {
        userCollection.remove(new ObjectId(id));
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return userCollection.findOne("(username : '" + username + "', password : '" + password + "')").as(User.class);
    }

    public User findUserByUsername(String username) {
        return userCollection.findOne("(username : '" + username + "')").as(User.class);
    }
}

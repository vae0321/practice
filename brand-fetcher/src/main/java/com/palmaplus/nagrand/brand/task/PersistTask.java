package com.palmaplus.nagrand.brand.task;

import static com.palmaplus.nagrand.brand.factory.EntityFactory.execute;

/**
 * Created by sifan on 2016/6/30.
 */
public class PersistTask<T> implements Runnable {

    private T entity;

    public PersistTask(T entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        save(entity);
    }

    private void save(T t) {
        execute(em -> em.merge(t));
    }

}

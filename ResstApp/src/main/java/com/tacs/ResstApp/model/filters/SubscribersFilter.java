package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class SubscribersFilter implements Filter{
    private Integer nofsubscribers;

    public SubscribersFilter(Integer nofsubscribers) {
        this.nofsubscribers = nofsubscribers;
    }

    @Override
    public boolean filter(Repository repository) {
        return false;
    }
}

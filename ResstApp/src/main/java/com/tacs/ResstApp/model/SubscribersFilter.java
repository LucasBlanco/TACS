package com.tacs.ResstApp.model;

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

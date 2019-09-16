package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class SubscribersFilter implements Filter {
    private Integer nofsubscribers;

    public Integer getNofsubscribers() {
    	return nofsubscribers;
    }
    
    public void setNofsubscribers(Integer nofsubscribers) {
    	this.nofsubscribers = nofsubscribers;
    }

    @Override
    public boolean filter(Repository repository) {
        return repository.getNofFaved() >= getNofsubscribers();
    }
}

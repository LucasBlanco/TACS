package com.tacs.ResstApp.model;

public class StarsFilter implements Filter {
    private Integer nofstars;

    public StarsFilter(Integer nofstars) {
        this.nofstars = nofstars;
    }

	@Override
	public boolean filter(Repository repository) {
		// TODO Auto-generated method stub
		return false;
	}
}

package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public interface Filter {
	public boolean filter(Repository repository);
}

package com.tacs.ResstApp.model.filters;

import java.util.List;

import com.tacs.ResstApp.model.Repository;

public interface Filter {
	public boolean filter(Repository repository);
}

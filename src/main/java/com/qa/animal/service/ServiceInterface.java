package com.qa.animal.service;

import java.util.Collection;

public interface ServiceInterface<T> {
	
	public T create(T t);
	public Collection<T> getAll();
	public T get(Integer id);
	public boolean replace(T t);
	public boolean remove(Integer id);
}

package com.mjc.school.repository;

public interface IdSequence<K> {

	K generateNewId();
}
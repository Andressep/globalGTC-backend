package com.example.globalgtcbackend.mappers;

public interface Mapper<S, T> {
    T transform(S source);
    S transformBack(T source);
}

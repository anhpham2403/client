package com.anh.movie.data.source;

/**
 * Created by MyPC on 10/05/2017.
 */

public interface SharePreferenceApi {
    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T data);

    void clear();

    void remove(String key);
}

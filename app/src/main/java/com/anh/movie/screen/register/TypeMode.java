package com.anh.movie.screen.register;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anh on 12/8/2017.
 */

@Retention(SOURCE)
@IntDef({
        TypeMode.REGISTER, TypeMode.UPDATE,
})
public @interface TypeMode {
    int REGISTER = 0;
    int UPDATE = 1;
}

package cn.southtree.ganku.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Southtree on 2017/11/15.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}

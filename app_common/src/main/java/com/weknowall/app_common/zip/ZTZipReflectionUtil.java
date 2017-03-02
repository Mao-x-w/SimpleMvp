package com.weknowall.app_common.zip;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ZTZipReflectionUtil {

  public static final String JAVA8_STREAM_API = "java.util.stream.Stream";

  private ZTZipReflectionUtil() {
  }

  static <T> Class<? extends T> getClassForName(String name, Class<T> clazz) {
    try {
      return Class.forName(name).asSubclass(clazz);
    }
    catch (ClassNotFoundException e) {
      throw new ZipException(e);
    }
    catch (ClassCastException e) {
      throw new ZipException(e);
    }
  }

  static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      return clazz.getDeclaredMethod(methodName, parameterTypes);
    }
    catch (NoSuchMethodException e) {
      throw new ZipException(e);
    }
  }

  static Object invoke(Method method, Object obj, Object... args) throws ZipException {
    try {
      return method.invoke(obj, args);
    }
    catch (IllegalAccessException e) {
      throw new ZipException(e);
    }
    catch (InvocationTargetException e) {
      throw new ZipException(e);
    }
    catch (IllegalArgumentException e) {
      throw new ZipException(e);
    }
  }

  public static boolean isClassAvailable(String className) {
    try {
      Class.forName(className);
      return true;
    }
    catch (ClassNotFoundException e) {
      // Ignore
    }
    catch (SecurityException e) {
      // Ignore
    }
    return false;
  }
}

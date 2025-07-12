package utils;

import exceptions.PathIsMissingException;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;

@Component
public class AnnotationUtils {

  /**
   * Retrieves an instance of the specified annotation from the given target class.
   *
   * @param targetClass     The class to inspect for the annotation.
   * @param annotationClass The annotation class to find.
   * @param <T>             The type of the annotation.
   * @return The annotation instance, if present.
   * @throws PathIsMissingException If the annotation is not present on the target class.
   */
  public <T extends Annotation> T getAnnotationInstance(Class<?> targetClass, Class<T> annotationClass) {
    if (targetClass == null || annotationClass == null) {
      throw new IllegalArgumentException("Target class and annotation class must not be null.");
    }
    if (targetClass.isAnnotationPresent(annotationClass)) {
      return targetClass.getAnnotation(annotationClass); // Allows inherited annotations
    }
    throw new PathIsMissingException(targetClass.getCanonicalName());
  }
}
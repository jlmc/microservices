package io.github.jlmc.chassis.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public final class Reflections {

    private static final Predicate<Field> ANY_FIELD = field -> true;

    private static final ConcurrentHashMap<Class<?>, List<Field>> CLASS_DECLARED_FIELDS_CACHES = new ConcurrentHashMap<>();

    private Reflections() {
        throw new AssertionError();
    }

    public static <T> T newInstance(String className) {
        Class<T> clazz = getClass(className);
        return newInstance(clazz);
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();

        } catch (InstantiationException e) {
            throw handleException(clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw handleException(clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            throw handleException(clazz.getName() + "#getDeclaredConstructor", e);
        } catch (InvocationTargetException e) {
            throw handleException(clazz.getName() + "#newInstance()", e);
        }
    }

    public static <T> Class<T> getClass(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw handleException(className, e);
        }
    }

    public static List<Annotation> getAllAnnotations(Class<?> type) {
        List<Annotation> annotations = new ArrayList<>();

        while (type != null) {
            Collections.addAll(annotations, type.getDeclaredAnnotations());
            type = type.getSuperclass();
        }

        return annotations;
    }

    /**
     * @param object     object type that to perform the introspection.
     * @param annotation annotation to filter the fields.
     * @return unique field annotated with parameter annotation, otherwise return empty optional.
     * @throws IllegalArgumentException if more the contains more than one field annotated with parameter annotation.
     */
    public static Optional<Field> getSingleDeclaredFieldWithAnnotation(Object object, Class<? extends Annotation> annotation) {
        return getSingleDeclaredFieldWithAnnotation(object.getClass(), annotation);
    }

    /**
     * @param type       class type that to perform the introspection.
     * @param annotation annotation to filter the fields.
     * @return unique field annotated with parameter annotation, otherwise return empty optional.
     * @throws IllegalArgumentException if more the contains more than one field annotated with parameter annotation.
     */
    public static Optional<Field> getSingleDeclaredFieldWithAnnotation(Class<?> type, Class<? extends Annotation> annotation) {
        List<Field> allDeclaredFieldsWithAnnotation = getAllDeclaredFieldsWithAnnotation(type, annotation);

        if (allDeclaredFieldsWithAnnotation.size() > 1) {
            throw new IllegalArgumentException("The type '" + type + "' contains more than one fields annotated with '" + annotation);
        }

        if (allDeclaredFieldsWithAnnotation.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(allDeclaredFieldsWithAnnotation.get(0));
    }

    public static List<Field> getAllDeclaredFieldsWithAnnotation(Class<?> type, Class<? extends Annotation> annotation) {
        Predicate<Field> fieldsWithAnnotationFilter = field -> field.isAnnotationPresent(annotation);

        return getAllDeclaredFields(type)
                .stream()
                .filter(fieldsWithAnnotationFilter)
                .collect(toList());
    }

    private static Optional<Field> getField(final Class<?> type, final String fieldName) {
        return getAllDeclaredFields(type)
                .stream()
                .filter(field -> fieldName.equals(field.getName()))
                .findFirst();
    }

    public static List<Field> getAllDeclaredFields(final Class<?> type) {
        if (CLASS_DECLARED_FIELDS_CACHES.containsKey(type)) {
            return CLASS_DECLARED_FIELDS_CACHES.get(type);
        }

        // List<Field> fields = new ArrayList<>();
        List<Field> allFields = getAllDeclaredFields(type, ANY_FIELD);

        CLASS_DECLARED_FIELDS_CACHES.putIfAbsent(type, allFields);

        return allFields;
    }

    private static List<Field> getAllDeclaredFields(final Class<?> type, final Predicate<Field> fieldFilter) {
        List<Field> fields = Arrays.stream(type.getDeclaredFields())
                .filter(fieldFilter)
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (type.getSuperclass() != null) {
            final List<Field> parentFields = getAllDeclaredFields(type.getSuperclass(), fieldFilter);
            fields.addAll(parentFields);
        }

        return fields;
    }

    public static void setFieldValue(final String fieldName, final Object object, final Object fieldNewValue) {
        getField(object.getClass(), fieldName)
                .ifPresent(field -> setFieldValue(field, object, fieldNewValue));
    }

    public static void setFieldValue(final Field field, final Object object, final Object fieldNewValue) {
        try {

            Objects.requireNonNull(field);
            Objects.requireNonNull(object);

            setAccessible(field);

            field.set(object, fieldNewValue);

        } catch (IllegalAccessException e) {
            throw handleException(field.getName(), e);
        }
    }

    public static <T> T getFieldValue(final Field field, final Object object, Class<T> expectedType) {
        try {
            setAccessible(field);

            Object o = field.get(object);
            return expectedType.cast(o);

        } catch (IllegalAccessException e) {
            throw handleException(field.getName(), e);
        }
    }

    public static Object getFieldValue(final Field field, final Object object) {
        try {

            setAccessible(field);

            return field.get(object);

        } catch (IllegalAccessException e) {
            throw handleException(field.getName(), e);
        }
    }

    private static void setAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static void copy(final Object source, final Object target) {
        copy(source, target, Collections.emptySet());
    }

    public static void copy(final Object source, final Object target, String fieldName) {
        copy(source, target, Collections.singleton(fieldName));
    }

    private static void copy(final Object source, final Object target, Set<String> ignoredFields) {
        Objects.requireNonNull(source, "source should not be null");
        Objects.requireNonNull(target, "target should not be null");
        Objects.requireNonNull(ignoredFields, "Ignored Fields should not be null");
        try {
            final Class<?> aClass = source.getClass();
            final List<Field> allDeclaredFields = getAllDeclaredFields(aClass);

            for (final Field field : allDeclaredFields) {
                if (!ignoredFields.contains(field.getName())) {
                    copyFieldValue(source, target, field);
                }

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void copyFieldValue(final Object source, final Object target, final Field field) {
        final Object fieldValue = getFieldValue(field, source);
        setFieldValue(field, target, fieldValue);
    }

    private static IllegalArgumentException handleException(String className, InstantiationException e) {
        return new IllegalArgumentException("Couldn't instantiate class " + className, e);
    }

    private static IllegalArgumentException handleException(String className, ClassNotFoundException e) {
        return new IllegalArgumentException("Couldn't find class " + className, e);
    }

    private static IllegalArgumentException handleException(String methodName, InvocationTargetException e) {
        return new IllegalArgumentException("Couldn't invoke method " + methodName, e);
    }

    private static IllegalArgumentException handleException(String memberName, IllegalAccessException e) {
        return new IllegalArgumentException("Couldn't access member " + memberName, e);
    }

    private static IllegalArgumentException handleException(String methodName, NoSuchMethodException e) {
        return new IllegalArgumentException("Couldn't find method " + methodName, e);
    }

    private static IllegalArgumentException handleException(String fieldName, NoSuchFieldException e) {
        return new IllegalArgumentException("Couldn't find field " + fieldName, e);
    }
}

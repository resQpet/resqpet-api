package service.domain.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import service.domain.exceptions.InternalErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseSpecification<T> implements Specification<T> {

    private static final String WILDCARD = "%";

    /**
     * Returns a wildcard pattern for performing a case-insensitive LIKE comparison between the given value and a path expression.
     *
     * @param value the value to be matched
     * @return a wildcard pattern for performing a case-insensitive LIKE comparison
     */
    private String like(String value) {
        return (WILDCARD + value + WILDCARD).toLowerCase();
    }

    /**
     * Returns a nested Path object for the given root Path and fields.
     *
     * @param root   the root Path object
     * @param fields the fields to navigate in the nested Path
     * @param <S>    the type of the nested Path
     * @return the nested Path object
     */
    protected <S> Path<S> getNestedPath(Path<T> root, String... fields) {
        //noinspection unchecked
        return (Path<S>) Arrays.stream(fields).reduce(root, Path::get, (u, v) -> {
            throw new InternalErrorException("Parallel Stream not supported");
        });
    }

    /**
     * Returns a predicate for performing an equal comparison between the given path expression and the given value.
     *
     * @param builder the criteria builder
     * @param path    the path expression to be compared
     * @param value   the value to be matched
     * @param <S>     the type of the value
     * @return a predicate for performing an equal comparison
     */
    protected <S extends Comparable<? super S>> Predicate getGreater(CriteriaBuilder builder, Expression<S> path, S value) {
        return builder.greaterThan(path, value);
    }

    /**
     * Returns a Predicate for performing a less-than comparison between the given path expression and the given value.
     *
     * @param builder the CriteriaBuilder
     * @param path    the path expression to be compared
     * @param value   the value to be matched
     * @param <S>     the type of the value
     * @return a Predicate for performing a less-than comparison
     */
    protected <S extends Comparable<? super S>> Predicate getLess(CriteriaBuilder builder, Expression<S> path, S value) {
        return builder.lessThan(path, value);
    }

    /**
     * Returns a predicate for performing a case-insensitive LIKE comparison between the given path expression and the given value.
     *
     * @param builder the criteria builder
     * @param path    the path expression to be compared
     * @param value   the value to be matched
     * @return a predicate for performing a case-insensitive LIKE comparison
     */
    protected Predicate getLike(CriteriaBuilder builder, Expression<String> path, String value) {
        return builder.like(builder.lower(path), like(value));
    }

    /**
     * Returns a predicate for performing an equal comparison between the given path expression and the given value.
     *
     * @param builder the criteria builder
     * @param path    the path expression to be compared
     * @param value   the value to be matched
     * @param <S>     the type of the value
     * @return a predicate for performing an equal comparison
     */
    protected <S> Predicate getEquals(CriteriaBuilder builder, Expression<String> path, S value) {
        return builder.equal(path, value);
    }

    /**
     * Adds a predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing an equal comparison between the given
     * path expression and the given value.
     *
     * @param <S>        the type of the value
     * @param predicates the list of predicates to add to
     * @param root       the root Path object
     * @param builder    the criteria builder
     * @param value      the value to be matched
     * @param paths      the paths to navigate in the nested Path
     */
    protected <S> void add(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, S value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<String> path = this.getNestedPath(root, paths);
            predicates.add(this.getEquals(builder, path, value));
        }
    }

    /**
     * Retrieves an optional object based on the provided criteria.
     *
     * @param <S>     the type of the value
     * @param root    the root Path object
     * @param builder the CriteriaBuilder
     * @param value   the value to be matched
     * @param paths   the paths to navigate in the nested Path
     * @return an Optional object containing the result of the retrieval, or empty if the value is null
     */
    protected <S> Optional<Predicate> get(Root<T> root, CriteriaBuilder builder, S value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<String> path = this.getNestedPath(root, paths);
            return Optional.ofNullable(this.getEquals(builder, path, value));
        }
        return Optional.empty();
    }

    /**
     * Adds a predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing a like comparison between the given path expression and the given value.
     *
     * @param predicates the list of predicates to add to
     * @param root       the root Path object
     * @param builder    the criteria builder
     * @param value      the value to be matched
     * @param paths      the paths to navigate in the nested Path
     */
    protected void add(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, String value, String... paths) {
        if (Objects.nonNull(value) && !value.isEmpty()) {
            final Path<String> path = this.getNestedPath(root, paths);
            predicates.add(this.getLike(builder, path, value));
        }
    }

    /**
     * Adds an empty predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing a LIKE comparison between the given path expression and the trimmed value.
     *
     * @param predicates the list of predicates to add to
     * @param root the root Path object
     * @param builder the criteria builder
     * @param value the value to be matched. Null values will not add a predicate.
     * @param paths the paths to navigate in the nested Path
     */
    protected void addEmpty(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, String value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<String> path = this.getNestedPath(root, paths);
            predicates.add(this.getLike(builder, path, value.trim()));
        }
    }

    /**
     * Adds a predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing an equal comparison between the given path expression and the given value.
     *
     * @param predicates the list of predicates to add to
     * @param root       the root Path object
     * @param builder    the criteria builder
     * @param value      the value to be matched
     * @param paths      the paths to navigate in the nested Path
     */
    protected void addEquals(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, String value, String... paths) {
        if (Objects.nonNull(value) && !value.isEmpty()) {
            final Path<String> path = this.getNestedPath(root, paths);
            predicates.add(this.getEquals(builder, path, value));
        }
    }

    /**
     * Adds a predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing a greater-than comparison between the given
     * path expression and the given value, if the value is not null.
     *
     * @param predicates the list of predicates to add to
     * @param root       the root Path object
     * @param builder    the criteria builder
     * @param value      the value to be compared with
     * @param paths      the paths to navigate in the nested Path
     * @param <S>        the type of the value
     */
    protected <S extends Comparable<? super S>> void greater(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, S value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<S> path = this.getNestedPath(root, paths);
            predicates.add(this.getGreater(builder, path, value));
        }
    }

    /**
     * Adds a predicate to the list of predicates based on the given value and paths.
     * The predicate is created by performing a less-than comparison between the given
     * path expression and the given value if the value is not null.
     *
     * @param predicates the list of predicates to add to
     * @param root       the root Path object
     * @param builder    the criteria builder
     * @param value      the value to be compared with
     * @param paths      the paths to navigate in the nested Path
     * @param <S>        the type of the value
     */
    protected <S extends Comparable<? super S>> void less(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, S value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<S> path = this.getNestedPath(root, paths);
            predicates.add(this.getLess(builder, path, value));
        }
    }

    protected <S> Predicate getNotEquals(CriteriaBuilder builder, Expression<S> path, S value) {
        return builder.notEqual(path, value);
    }

    protected <S> void addNotEquals(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, S value, String... paths) {
        if (Objects.nonNull(value)) {
            final Path<S> path = this.getNestedPath(root, paths);
            predicates.add(this.getNotEquals(builder, path, value));
        }
    }

    protected <S> void addIn(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, List<S> values, String... paths) {
        if (Objects.nonNull(values) && !values.isEmpty()) {
            final Path<S> path = this.getNestedPath(root, paths);
            predicates.add(path.in(values));
        }
    }

    protected <S extends Comparable<? super S>> void addBetween(List<Predicate> predicates, Root<T> root, CriteriaBuilder builder, S start, S end, String... paths) {
        if (Objects.nonNull(start) &&  Objects.nonNull(end)) {
            final Path<S> path = this.getNestedPath(root, paths);
            predicates.add(builder.between(path, start, end));
        } else if (Objects.nonNull(start)) {
            greater(predicates, root, builder, start, paths);
        } else if (Objects.nonNull(end)) {
            less(predicates, root, builder, end, paths);
        }
    }

}

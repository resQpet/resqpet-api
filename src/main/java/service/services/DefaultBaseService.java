package service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.BaseEntity;
import service.domain.exceptions.NotFoundException;
import service.domain.repository.BaseRepository;

public abstract class DefaultBaseService<T extends BaseEntity> implements BaseService<T> {

    private static final String EMPTY = "";
    private static final String WILDCARD = "Service";
    public static final String NOT_FOUND_MESSAGE = "No se pudo encontrar el registro con ID: %s. %s.";

    @Override
    public T save(T t) {
        return this.getRepository().save(t);
    }

    @Transactional
    public T create(T entity) {
        return this.getRepository().save(entity);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return this.getRepository().findAll(pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable, Specification<T> specification) {
        return this.findAll(specification, pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return this.getRepository().findAll(specification, pageable);
    }

    @Override
    public T findById(Long id) {
        return this.getRepository().findById(id).orElseThrow(() -> {
            final String ENTITY_NAME = getClass().getSimpleName().replace(WILDCARD, EMPTY);
            final String message = String.format(NOT_FOUND_MESSAGE, id, ENTITY_NAME);
            return new NotFoundException(message);
        });
    }

    protected abstract BaseRepository<T> getRepository();

}

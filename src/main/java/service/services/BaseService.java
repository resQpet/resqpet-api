package service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import service.domain.entity.BaseEntity;
import service.domain.repository.BaseRepository;

public interface BaseService<T extends BaseEntity> {

    T save(T t);

    T create(T entity);

    T findById(Long id);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Pageable pageable, Specification<T> specification);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    static <T extends BaseEntity> BaseService<T> of(@NonNull final BaseRepository<T> repository) {
        return new DefaultBaseService<>() {
            @Override
            protected BaseRepository<T> getRepository() {
                return repository;
            }
        };
    }
}

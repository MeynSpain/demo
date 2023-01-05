package com.example.demo.repo;

import com.example.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Итерфейс для работы с таблицей Post в базе данных,
 * ничего здесь не добавляем, потому что все необходимые методы уже наследованы
 * от CrudRepository
 */
public interface PostRepository extends CrudRepository<Post, Long> {
}

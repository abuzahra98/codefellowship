package com.example.codefellowship.repositories;

import com.example.codefellowship.modal.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {
}

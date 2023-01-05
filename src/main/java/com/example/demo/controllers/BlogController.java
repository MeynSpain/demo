package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    /**
     * Интерфейс для взаимодействия с таблицей Post
     */
    @Autowired
    private PostRepository postRepository;

    //Отслеживаем ссылку "/blog"
    @GetMapping("/blog")
    public String blogMain(Model model){

        //Получаем все записи из таблицы Post
        Iterable<Post> posts = postRepository.findAll();

        //Теперь передаем все записи в шаблон, чтобы отобразить их на странице
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){



        return "blog-add";
    }
}

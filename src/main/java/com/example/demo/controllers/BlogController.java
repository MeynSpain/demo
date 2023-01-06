package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    /**
     * Интерфейс для взаимодействия с таблицей Post
     */
    @Autowired
    private PostRepository postRepository;

    //Отслеживаем ссылку "/blog"
    @GetMapping("/blog")
    public String blogMain(Model model) {

        //Получаем все записи из таблицы Post
        Iterable<Post> posts = postRepository.findAll();

        //Теперь передаем все записи в шаблон, чтобы отобразить их на странице
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    /**
     * Переход на страницу добавления поста
     *
     * @param model
     * @return
     */
    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    /**
     * Публикация поста
     *
     * @param title     название поста
     * @param anons     анонс
     * @param full_text полный текст
     * @param model
     * @return
     */
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text,
                              Model model) {
        //Создаем пост
        Post post = new Post(title, anons, full_text);

        //Сохраняем его в БД
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/post/{id}")
    public String blogPostDetails(@PathVariable(value = "id") Long id, Model model) {

        //Если такого поста нет, то перенаправляем на страницу с постами
        if (!postRepository.existsById(id))
            return "redirect:/blog";

        //Ищем пост по id
        Optional<Post> post = postRepository.findById(id);

        //Создаем array list, чтобы добавить кго в модель
        ArrayList<Post> result = new ArrayList<>();

        //Ищем пост и если нашли, то добавляем в array list
        post.ifPresent(result::add);

        //Добавляем лист в модель
        model.addAttribute("post", result);

        return "post-details";
    }


    @GetMapping("/post/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") Long id, Model model) {

        //Если такого поста нет, то перенаправляем на страницу с постами
        if (!postRepository.existsById(id))
            return "redirect:/blog";

        //Ищем пост по id
        Optional<Post> post = postRepository.findById(id);

        //Создаем array list, чтобы добавить кго в модель
        ArrayList<Post> result = new ArrayList<>();

        //Ищем пост и если нашли, то добавляем в array list
        post.ifPresent(result::add);

        //Добавляем лист в модель
        model.addAttribute("post", result);

        return "post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") Long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model) {

        Post post = postRepository.findById(id).orElseThrow();

        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);

        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/post/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") Long id, Model model) {

        Post post = postRepository.findById(id).orElseThrow();


        postRepository.delete(post);

        return "redirect:/blog";
    }
}

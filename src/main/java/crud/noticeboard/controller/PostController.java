package crud.noticeboard.controller;

import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.PostCreateDto;
import crud.noticeboard.repository.PostRepository;
import crud.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/postList")
    public String post(Model model, @PageableDefault(size = 2, sort = "id") Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        model.addAttribute("posts", posts);

        return "/postList";
    }

    @GetMapping("/posts/new")
    public String postWrite(Model model){
        model.addAttribute("PostCreateDto", new PostCreateDto());
        return "/createPost";
    }

    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute("PostCreateDto") @Valid PostCreateDto postCreateDto, BindingResult result){

        if(result.hasErrors()){
            return "/createPost";
        }

        postService.createPost(postCreateDto.getTitle(), postCreateDto.getContent());

        return "redirect:/postList";
    }
}

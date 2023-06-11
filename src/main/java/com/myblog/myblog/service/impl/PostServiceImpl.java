package com.myblog.myblog.service.impl;

import com.myblog.myblog.entity.post;
import com.myblog.myblog.exception.ResourceNotFoundException;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;
import com.myblog.myblog.repository.PostRepository;
import com.myblog.myblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {

        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        post post = mapToEntity(postDto);
        post newPost = postRepository.save(post);

        PostDto postResponse = mapTODto(newPost);
        return postResponse;


    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();


        PageRequest pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<post> content = postRepository.findAll(pageable);
        List<post> posts = content.getContent();

        List<PostDto> collect = posts.stream().map(post -> mapTODto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collect);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalElements(content.getNumberOfElements());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setLast(content.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        post Post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","Id",id)
        );
        return mapTODto(Post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(post.getDescription());
        post.setContent(postDto.getContent());

        post updatedPost = postRepository.save(post);
        return mapTODto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        post post =postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        postRepository.deleteById(id);
    }

    private post mapToEntity(PostDto postDto){
        post post = mapper.map(postDto, post.class);
        return post;
    }

    private PostDto mapTODto(post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }
}

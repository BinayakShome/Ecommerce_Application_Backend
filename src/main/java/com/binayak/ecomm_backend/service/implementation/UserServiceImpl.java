package com.binayak.ecomm_backend.service.implementation;

import com.binayak.ecomm_backend.entity.User;
import com.binayak.ecomm_backend.exception.ResourceNotFoundException;
import com.binayak.ecomm_backend.payload.UserDto;
import com.binayak.ecomm_backend.repo.RoleRepo;
import com.binayak.ecomm_backend.repo.UserRepo;
import com.binayak.ecomm_backend.response.UserResponse;
import com.binayak.ecomm_backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> pagePost = this.userRepo.findAll(page);
        List<User> allPosts = pagePost.getContent();

        List<UserDto> posts = allPosts
                .stream()
                .map((post -> this.modelMapper.map(post, UserDto.class)))
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(posts);
        userResponse.setPageNumber(pagePost.getNumber());
        userResponse.setPageSize(pagePost.getSize());
        userResponse.setLastPage(pagePost.isLast());
        userResponse.setTotalElements(pagePost.getNumberOfElements());
        userResponse.setTotalPages(pagePost.getTotalPages());

        return userResponse;
    }

    @Override
    public UserResponse searchUser(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> filterPosts = this.userRepo.findByUserNameContainingIgnoreCase(keyword, pageable);
        List<UserDto> filtered = filterPosts.stream().map(posts -> this.modelMapper.map(posts, UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(filtered);

        userResponse.setPageNumber(filterPosts.getNumber());
        userResponse.setPageSize(filterPosts.getSize());
        userResponse.setTotalElements(filterPosts.getTotalElements());
        userResponse.setTotalPages(filterPosts.getTotalPages());
        userResponse.setLastPage(filterPosts.isLast());

        return userResponse;
    }
}

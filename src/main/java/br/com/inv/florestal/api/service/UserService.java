package br.com.inv.florestal.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public Page<UserRepresentation> search(Integer page, Integer size){
        return this.userRepository.search(PageRequest.of(page, size));
    }

}

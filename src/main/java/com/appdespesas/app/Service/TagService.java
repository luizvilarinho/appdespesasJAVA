package com.appdespesas.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdespesas.app.DTO.TagDto;
import com.appdespesas.app.Entity.Tag;
import com.appdespesas.app.repository.TagRepository;
import com.appdespesas.app.util.CopyUtils;

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	CopyUtils copyUtils;
	
	public List<TagDto> retrieveAllTags(){
		List<Tag> tags =  this.tagRepository.findAll();
		
		System.out.println(tags);
		
		List<TagDto> dto = tags.stream()
				.map(t -> copyUtils.toTagDTO(t))
				.collect(Collectors.toList());
		
		return dto;
	}
	
	public TagDto saveNewTag(TagDto dto) {
		Tag tag = new Tag(dto);
		
		this.tagRepository.save(tag);
		
		return copyUtils.toTagDTO(tag);
	}
	
	public TagDto deleteTag(Integer id) {
		Optional<Tag> tag = tagRepository.findById(id);
		
		if(tag.isPresent()) {
			tagRepository.deleteById(id);
			return copyUtils.toTagDTO(tag.get());
		}else {
			return null;
		}
	}
	
}

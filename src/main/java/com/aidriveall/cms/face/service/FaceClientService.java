package com.aidriveall.cms.face.service;

import com.aidriveall.cms.face.dto.FaceResultDTO;
import com.aidriveall.cms.face.dto.PersonFaceDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

@Service
public class FaceClientService {
    public final String FACE_SERVER_URL = "http://localhost:8080";
    private RestTemplate restTemplate=new RestTemplate();

    public PersonFaceDTO saveFace(String id, String uniqueId, String name,String cardNo, File file) {
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("id", id);
        param.add("name", name);
        param.add("cardNo", cardNo);
        param.add("image", resource);
        param.add("uniqueId", uniqueId);
        ResponseEntity<PersonFaceDTO> personFaceDTO = restTemplate.postForEntity(FACE_SERVER_URL + "/api/person-faces/form", param, PersonFaceDTO.class);
        return personFaceDTO.getBody();
    }

    public FaceResultDTO findOnePerson(File file) {
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("image", resource);
        ResponseEntity<FaceResultDTO> responseEntity = restTemplate.postForEntity(FACE_SERVER_URL + "/api/person-faces/person", param, FaceResultDTO.class);
        return responseEntity.getBody();
    }

    public List<FaceResultDTO> findAllPerson(File file) {
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("image", resource);
        ResponseEntity<List> responseEntity = restTemplate.postForEntity(FACE_SERVER_URL + "/api/person-faces/persons", param, List.class);
        return (List<FaceResultDTO>) responseEntity.getBody();
    }
}

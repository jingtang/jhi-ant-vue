package com.aidriveall.cms.face.dto;

public class FaceResultDTO {
    private FaceSimilar similar;
    private PersonFaceDTO person;

    public FaceSimilar getSimilar() {
        return similar;
    }

    public void setSimilar(FaceSimilar similar) {
        this.similar = similar;
    }

    public PersonFaceDTO getPerson() {
        return person;
    }

    public void setPerson(PersonFaceDTO person) {
        this.person = person;
    }
}

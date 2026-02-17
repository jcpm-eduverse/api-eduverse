package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.ClassRoomCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Institution;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private InstitutionRepository institutionRepository; // Obrigatório para o vínculo

    public ClassRoom createClassRoom(ClassRoomCreateDTO dto) {
        // 1. Validação
        Institution institution = institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Erro: Instituição com ID " + dto.getInstitutionId() + " não encontrada."));

        // 2. Criação da Sala
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(dto.getName());

        // 3. Vínculo
        classRoom.setInstitution(institution);

        // 4. Salvar
        return classRoomRepository.save(classRoom);
    }
    // Método para listar todas as turmas
    public java.util.List<ClassRoom> getAllClassRooms() {
        return classRoomRepository.findAll();
    }
}
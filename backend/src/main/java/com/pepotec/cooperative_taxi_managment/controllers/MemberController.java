package com.pepotec.cooperative_taxi_managment.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.pepotec.cooperative_taxi_managment.services.MemberService;
import com.pepotec.cooperative_taxi_managment.models.dto.MemberDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import com.pepotec.cooperative_taxi_managment.exceptions.InvalidDataException;

/**
 * Controller para gestionar los miembros de la cooperativa
 */
@Tag(
    name = "Members",
    description = "API para la gestión de miembros de la cooperativa"
)
@RestController
@RequestMapping("/api/members")
public class MemberController {
    
    @Autowired
    private MemberService memberService;

    /**
     * Crear un nuevo miembro
     */
    @Operation(
        summary = "Crear un nuevo miembro",
        description = "Crea un nuevo miembro con la información proporcionada.",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Miembro creado exitosamente", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Datos inválidos"
            ),
            @ApiResponse(
                responseCode = "409", 
                description = "Conflicto - DNI, CUIT o Email ya existen"
            )
        }
    )
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO member) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.createMember(member));
    }

    /**
     * Actualizar un miembro existente
     */
    @Operation(
        summary = "Actualizar un miembro existente",
        description = "Actualiza los datos de un miembro existente por su ID.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Miembro actualizado exitosamente", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Datos inválidos"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Miembro no encontrado"
            ),
            @ApiResponse(
                responseCode = "409", 
                description = "Conflicto - DNI, CUIT o Email ya existen"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(
        @PathVariable Long id, 
        @Valid @RequestBody MemberDTO member
    ) {
        // Validar que el ID del body coincida con el ID del path (si se envió)
        if (member.getId() != null && !member.getId().equals(id)) {
            throw new InvalidDataException(
                String.format("El ID del body (%d) no coincide con el ID de la URL (%d)", 
                    member.getId(), id)
            );
        }
        
        member.setId(id);
        return ResponseEntity.ok(memberService.updateMember(member));
    }

    /**
     * Eliminar (dar de baja) un miembro
     */
    @Operation(
        summary = "Eliminar un miembro",
        description = "Da de baja un miembro por su ID (soft delete).",
        responses = {
            @ApiResponse(
                responseCode = "204", 
                description = "Miembro eliminado exitosamente"
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "ID inválido"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Miembro no encontrado"
            ),
            @ApiResponse(
                responseCode = "409", 
                description = "El miembro ya está inactivo"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener un miembro por su ID
     */
    @Operation(
        summary = "Obtener un miembro por ID",
        description = "Busca y retorna un miembro por su ID.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Miembro encontrado", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Miembro no encontrado"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * Obtener un miembro por su DNI
     */
    @Operation(
        summary = "Obtener un miembro por DNI",
        description = "Busca y retorna un miembro activo por su DNI.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Miembro encontrado", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "DNI inválido"
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Miembro no encontrado"
            )
        }
    )
    @GetMapping("/dni/{dni}")
    public ResponseEntity<MemberDTO> getMemberByDni(@PathVariable String dni) {
        return ResponseEntity.ok(memberService.getMemberByDni(dni));
    }

    /**
     * Obtener todos los miembros
     */
    @Operation(
        summary = "Obtener todos los miembros",
        description = "Retorna una lista de todos los miembros (activos e inactivos).",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Lista de miembros retornada exitosamente", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * Obtener todos los miembros activos
     */
    @Operation(
        summary = "Obtener todos los miembros activos",
        description = "Retorna una lista de todos los miembros activos.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Lista de miembros activos retornada exitosamente", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                )
            )
        }
    )
    @GetMapping("/active")
    public ResponseEntity<List<MemberDTO>> getAllMembersActive() {
        return ResponseEntity.ok(memberService.getAllMembersActive());
    }
}

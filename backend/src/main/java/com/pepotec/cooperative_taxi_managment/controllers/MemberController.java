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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@Tag(
    name = "Member",
    description = "Controller for managing members"
)
@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Operation(
        summary = "Create a new member",
        description = "Creates a new member with the given information.",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Member created successfully", 
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberDTO.class)
                    )
                )
        }
    )
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO member) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.createMember(member));
    }

    @Operation(
        summary = "Update an existing member",
        description = "Updates an existing member with the given information.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Member updated successfully", 
                content = @Content(
                    schema = @Schema(implementation = MemberDTO.class)
                    )
                )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(
        @PathVariable Long id, 
        @Valid @RequestBody MemberDTO member
        ){
        member.setId(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.updateMember(member));
    }

    @Operation(
        summary = "Delete an existing member",
        description = "Deletes an existing member with the given information.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Member deleted successfully", 
                content = @Content(schema = @Schema(implementation = MemberDTO.class)
                )
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK).body("Member deleted successfully");
    }

    @Operation(
        summary = "Get a member by id",
        description = "Gets a member by id.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Member found", content = @Content(schema = @Schema(implementation = MemberDTO.class)))
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberById(id));
    }

    @Operation(
        summary = "Get a member by dni",
        description = "Gets a member by dni.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Member found", content = @Content(schema = @Schema(implementation = MemberDTO.class)))
        }
    )
    @GetMapping("/dni/{dni}")
    public ResponseEntity<MemberDTO> getMemberByDni(@PathVariable String dni) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberByDni(dni));
    }

    @Operation(
        summary = "Get all members",
        description = "Gets all members.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Members found", content = @Content(schema = @Schema(implementation = MemberDTO.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getAllMembers());
    }
}


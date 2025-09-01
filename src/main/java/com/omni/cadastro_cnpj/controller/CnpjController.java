package com.omni.cadastro_cnpj.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omni.cadastro_cnpj.dto.CnpjDTO;
import com.omni.cadastro_cnpj.service.interfaces.ICnpjService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/omni")
public class CnpjController {
    
    private final ICnpjService cnpjService;

    public CnpjController(ICnpjService cnpjService) {
        this.cnpjService = cnpjService;
    }
    
    
    @Operation(summary = "Cria um novo CNPJ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CNPJ criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "CNPJ já existe")})
    @PostMapping("/criar_cnpj")
    public ResponseEntity<CnpjDTO> criar(@Valid @RequestBody CnpjDTO cnpj) { 
        return ResponseEntity.ok(cnpjService.salvar(cnpj));
    }

    @Operation(summary = "Lista todos os CNPJs")
    @GetMapping("/buscar")
    public ResponseEntity<List<CnpjDTO>> listarTodos() {
        return ResponseEntity.ok(cnpjService.listarTodos());
    }

    @Operation(summary = "Buscar por o CNPJ por ID")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CnpjDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cnpjService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar o CNPJ por ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CnpjDTO> atualizar(@PathVariable Long id,@Valid @RequestBody CnpjDTO cnpjAtualizado) {
        return ResponseEntity.ok(cnpjService.atualizar(id, cnpjAtualizado));
    }
    
    @Operation(summary = "Deletar o CNPJ por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cnpjService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
package com.mmelo.financial.web.controller;

import com.mmelo.financial.domain.CompanyDTO;
import com.mmelo.financial.persistence.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyController.COMPANY_ENDPOINT)
public class CompanyController {

    public static final String COMPANY_ENDPOINT = "/company";

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('REVIEW')")
    public List<CompanyDTO> findAll() {
        return companyService.findAll()
                .stream()
                .map(rule -> modelMapper.map(rule, CompanyDTO.class))
                .collect(Collectors.toList());
    }
}
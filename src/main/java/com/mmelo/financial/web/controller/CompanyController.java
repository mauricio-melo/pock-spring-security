package com.mmelo.financial.web.controller;

import com.mmelo.financial.persistence.entity.Company;
import com.mmelo.financial.persistence.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyController.COMPANY_ENDPOINT)
public class CompanyController {

    public static final String COMPANY_ENDPOINT = "/company";

    private final CompanyService companyService;

    @GetMapping
    @PreAuthorize("hasRole('REVIEW')")
    public List<Company> findAll() {
        return companyService.findAll();
    }
}
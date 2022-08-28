package com.example.globalO2O.sor.Controller;

import com.example.globalO2O.sor.DTO.ApiDto;
import com.example.globalO2O.sor.DTO.ApiResultDto;
import com.example.globalO2O.sor.Service.searchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/post-api")
public class ApiPostController {

    searchService search;

    @Autowired
    private ApiPostController(searchService search){
        this.search = search;
    }

    @PostMapping("/request")
    public ApiResultDto initial(@RequestBody ApiDto dto) throws IOException {
        if(!search.isRegist(Integer.parseInt(dto.getAnnNo())))
            search.registAnn(Integer.parseInt(dto.getAnnNo()));
        return search.findAnnNo(dto);
    }
}

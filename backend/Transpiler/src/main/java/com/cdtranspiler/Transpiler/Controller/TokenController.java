package com.cdtranspiler.Transpiler.Controller;

import com.cdtranspiler.Transpiler.DTO.TokenRequest;
import com.cdtranspiler.Transpiler.DTO.TokenResponse;
import com.cdtranspiler.Transpiler.Models.TokenModel;
import com.cdtranspiler.Transpiler.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tService;
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addCode(@RequestBody  TokenRequest Trequest)
    {
        tService.addCode(Trequest);
        return "Code added successfully";
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<TokenResponse> getAllCode()
    {
        return tService.getAllCodes();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse getCodeById(@PathVariable Long id)
    {
        return tService.getCodebyId(id);
    }

}

package com.cdtranspiler.Transpiler.Service;

import com.cdtranspiler.Transpiler.Config.ResourceNotFound;
import com.cdtranspiler.Transpiler.DTO.TokenRequest;
import com.cdtranspiler.Transpiler.DTO.TokenResponse;
import com.cdtranspiler.Transpiler.Helper.Tokens;
import com.cdtranspiler.Transpiler.Models.TokenModel;
import com.cdtranspiler.Transpiler.Respository.TokenReposiitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private Tokens tokens;
    private final TokenReposiitory tokenReposiitory;

    public void addCode(TokenRequest tRequest)
    {
        TokenModel tModel = TokenModel.builder()
                .source_code(tRequest.getSourcecode())
                .build();

        tokenReposiitory.save(tModel);
    }

    public List<TokenResponse> getAllCodes()
    {
        return tokenReposiitory.findAll().stream().map(this::MaptoResponse).toList();
    }

    public TokenResponse MaptoResponse(TokenModel tModel)
    {
        tokens = new Tokens(tModel.getSource_code());
        tokens.parse_c(0);
        String target = tokens.parse_c_to_python();
        return TokenResponse.builder()
                .id(tModel.getId())
                .sourcecode(tModel.getSource_code())
                .targetcode(target)
                .build();
    }

    public TokenResponse getCodebyId(Long id)
    {

        TokenModel tModel =  tokenReposiitory.findById(id).orElseThrow(()-> new ResourceNotFound("Code", id));
        tokens = new Tokens(tModel.getSource_code());
        tokens.parse_c(0);
        String target = tokens.parse_c_to_python();
        return TokenResponse.builder().id(tModel.getId())
                .sourcecode(tModel.getSource_code())
                .targetcode(target)
                .build();
    }


}

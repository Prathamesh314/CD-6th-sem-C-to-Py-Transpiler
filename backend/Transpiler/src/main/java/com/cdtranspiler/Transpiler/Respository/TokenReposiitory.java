package com.cdtranspiler.Transpiler.Respository;

import com.cdtranspiler.Transpiler.Models.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenReposiitory extends JpaRepository<TokenModel, Long> {

}

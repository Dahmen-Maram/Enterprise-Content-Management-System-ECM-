package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.Tokens;
import com.bfi.ecm.Services.ServiceImplementation.IndexationTxt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bfi/v1/tokens")
public class TokenController {
    private final IndexationTxt indexationTxt;

    public TokenController(IndexationTxt indexationTxt) {
        this.indexationTxt = indexationTxt;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveToken(@RequestParam String text) throws IOException {
        indexationTxt.saveToken(text);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tokens>> searchBySoundex(@RequestParam String soundexCode) {
        List<Tokens> tokens = indexationTxt.findBySoundexCode(soundexCode);
        return ResponseEntity.ok(tokens);
    }
}

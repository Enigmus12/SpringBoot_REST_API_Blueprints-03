package edu.eci.arsw.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GrammarChecker {

    @Autowired
    @Qualifier("spanishSpellChecker") // <-- Aquí elegimos cuál implementation inyectar
    private SpellChecker sc;

    public String check(String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("Spell checking output:" + sc.checkSpell(text));
        sb.append(" Plagiarism checking output: Not available yet");
        return sb.toString();
    }

}

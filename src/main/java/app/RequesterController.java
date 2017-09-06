package app;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RequesterController {

    @RequestMapping("/mutants")
    public RequesterDto requester(@RequestBody Map<String, List<String>> dna) {
        List<String> dnaSequence = dna.get("dna");
        if(!isMutante(dnaSequence.toArray(new String[dnaSequence.size()]))) {
            throw new ForbiddenException();
        }
        return null;
    }

    private boolean isMutante(String[] adn) {
        int totalMatches = 0;
        for (int i = 0; i < adn.length && totalMatches <= 1; i ++) {
            for(int j = 0; j < adn[i].length() && totalMatches <= 1; j ++) {
                Character currentChar = adn[i].charAt(j);
                if(comparePositions(currentChar, adn, i, j, adn.length, adn[i].length(),0,1)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),0,2)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),0,3)) {
                    totalMatches++;
                }
                if(comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),1,0)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),2,0)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),3,0)){
                    totalMatches++;
                }
                if(comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),1,1)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),2,2)
                        && comparePositions(currentChar, adn, i, j, adn.length,adn[i].length(),3,3)) {
                    totalMatches++;
                }
            }
        }
        return totalMatches > 1;
    }

    private boolean comparePositions(Character curChar, String[] array, int xIndex, int yIndex, int xMaxSize, int yMaxSize, int xIncrement, int yIncrement) {
        if(xIndex + xIncrement < xMaxSize && yIndex + yIncrement < yMaxSize) {
            return curChar == array[xIndex+xIncrement].charAt(yIndex+yIncrement);
        }
        return false;
    }
}
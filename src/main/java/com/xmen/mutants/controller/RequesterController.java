package com.xmen.mutants.controller;

import com.xmen.mutants.dto.StatsDto;
import com.xmen.mutants.exception.BadRequestException;
import com.xmen.mutants.exception.ForbiddenException;
import com.xmen.mutants.model.Request;
import com.xmen.mutants.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mutant Validator REST API.
 *
 * @author Caetano Riverón
 */

@RestController
public class RequesterController {

    public final static String HUMANS = "Humans";
    public final static String MUTANTS = "Mutants";
    private final static Set<Character> validDNASequenceCharacters = Collections
            .unmodifiableSet(new HashSet<>(Arrays
                    .asList('A','T','C','G')));

    @Autowired
    private RequestService requestService;

    /**
     * Checks whether the requester is a mutant or not based on the DNA sequence that was sent.
     *
     * @param dna JSON body containing a valid DNA sequence (only contains A, T, C or G characters)
     *          in the form of a single key containing an array.
     *          e.g: {"dna" : ["AAATCG", "TTACGGT"]}
     *
     * @throws BadRequestException if the body doesn't contain a valid DNA sequence
     * @throws ForbiddenException if the DNA sequence isn't from a mutant.
     */

    @RequestMapping(method = RequestMethod.POST, value = "/mutants")
    public void requester(@RequestBody Map<String, List<String>> dna) {
        List<String> dnaSequence = dna.get("dna");

        for(String string : dnaSequence) {
            for(Character character : string.toCharArray()) {
                if(!validDNASequenceCharacters.contains(character)) {
                    throw new BadRequestException("Payload isn't a DNA sequence");
                }
            }
        }

        if(!isMutante(dnaSequence.toArray(new String[dnaSequence.size()]))) {
            requestService.addRequest(new Request(false));
            throw new ForbiddenException();
        }
        requestService.addRequest(new Request(true));
    }

    /**
     * Returns the total amount of mutants and humans that requested the /mutants service and a ratio of mutants/humans.
     */

    @RequestMapping(method = RequestMethod.GET, value = "/stats")
    public StatsDto stats() {
        Map<String, Double> stats = requestService.getMutantAndHumanRequests();
        Double humans = stats.get(HUMANS);
        Double mutants = stats.get(MUTANTS);
        return new StatsDto(mutants, humans, humans != 0 ? (mutants / humans): 0);
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
        return xIndex + xIncrement < xMaxSize && yIndex + yIncrement < yMaxSize && curChar == array[xIndex + xIncrement].charAt(yIndex + yIncrement);
    }
}
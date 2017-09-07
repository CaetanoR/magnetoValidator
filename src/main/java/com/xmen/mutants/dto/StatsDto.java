package com.xmen.mutants.dto;

/**
 * Data Access Object representing the Json output from the Stats service.
 *
 * @author Caetano Riverón
 */

public class StatsDto {

    private Double count_mutant_dna;
    private Double count_human_dna;
    private Double ratio;

    public StatsDto(Double count_mutant_dna, Double count_human_dna, Double ratio) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = ratio;
    }

    public Double getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public Double getCount_human_dna() {
        return count_human_dna;
    }

    public Double getRatio() {
        return ratio;
    }
}

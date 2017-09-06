package app;

import java.util.List;

public class RequesterDto {

    private List<String> dna;

    public RequesterDto(List<String> dna) {
        this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }
}

Mutant Validator REST API.
=============================

The Magneto Validator API allows you to send a POST request to validate whether a DNA sequence belongs to a mutant or not. It also allows to set a GET request to get statistics about the mutant/human ratio of requesters.

## Author
Caetano Riverón

## Endpoints

* *POST* `/mutants`
  * Json request body example:
```json
{
    "dna":
    ["ATGCGA",
     "CAGTGC",
     "TTATGT",
     "AGAAGG",
     "CCCCTA",
     "TCACTA"
    ]
}
```
* *GET* `/stats`
  * Json response body example:
```json
{
    "count_mutant_dna": 24,
    "count_human_dna": 12,
    "ratio": 2
}
```

## Usage

See the [REST API RAML](src/main/api/magnetoValidator.raml) for detailed API scope.

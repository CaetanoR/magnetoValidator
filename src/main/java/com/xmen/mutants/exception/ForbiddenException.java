package com.xmen.mutants.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the requester's DNA sequence isn't a mutant's.
 *
 * @author Caetano Riverón
 */

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
}
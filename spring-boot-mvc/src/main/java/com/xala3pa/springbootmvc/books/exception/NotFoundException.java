package com.xala3pa.springbootmvc.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Book")
public class NotFoundException extends RuntimeException {

}

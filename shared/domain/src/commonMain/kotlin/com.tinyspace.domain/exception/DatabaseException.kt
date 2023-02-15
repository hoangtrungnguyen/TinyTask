package com.tinyspace.domain.exception

abstract class DatabaseException(message: String) : Exception(message){
}


class InsertErrorException : DatabaseException(
    "Can't insert to the database, please retry")
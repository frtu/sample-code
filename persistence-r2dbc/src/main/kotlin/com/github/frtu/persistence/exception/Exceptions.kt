package com.github.frtu.persistence.exception

import java.lang.IllegalArgumentException

class DataNotExist(id: String) : IllegalArgumentException("Id doesn't exist ${id}")
package com.github.frtu.coroutine.exception

import java.lang.IllegalArgumentException

class DataNotExist(id: String) : IllegalArgumentException("Id doesn't exist ${id}")
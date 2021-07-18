package com.github.frtu.sample.resilience.ratelimiter.redis

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.SessionCallback
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class SimpleRedisRateLimiter(
    val limitForPeriod: Int = 5,
    val timeoutDuration: Long = 2,
    val unit: TimeUnit = TimeUnit.HOURS
) {
    @Autowired
    lateinit var stringTemplate: StringRedisTemplate

    fun isLimitReached(uniqueKey: String): Boolean {
        val hour = LocalDateTime.now().hour
        val key = "$uniqueKey:$hour"

        val operations: ValueOperations<String, String> = stringTemplate.opsForValue()
        operations.get(key)?.let { requests ->
            if (StringUtils.hasText(requests) && requests.toInt() >= limitForPeriod) {
                return true
            }
        }

        val txResults: List<Any> = stringTemplate.execute(object : SessionCallback<List<Any>> {
            @Throws(DataAccessException::class)
            override fun <K, V> execute(operations: RedisOperations<K, V>): List<Any> {
                val redisTemplate: StringRedisTemplate = operations as StringRedisTemplate
                val valueOperations: ValueOperations<String, String> = redisTemplate.opsForValue()
                operations.multi()
                valueOperations.increment(key)
                redisTemplate.expire(key, timeoutDuration, unit)
                return operations.exec()
            }
        })
        logger.info("Current request count: " + txResults[0])
        return false
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}
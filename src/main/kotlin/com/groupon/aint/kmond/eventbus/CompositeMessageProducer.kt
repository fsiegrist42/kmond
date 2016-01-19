/**
 * Copyright 2015 Groupon Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.groupon.aint.kmond.eventbus

import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.MessageProducer

/**
 *
 *
 * @author Gil Markham (gil at groupon dot com)
 */
class CompositeMessageProducer<T>(vararg val senders: MessageProducer<T>): MessageProducer<T> {
    override fun write(data: T): MessageProducer<T>? {
        senders.forEach { it.write(data) }
        return this
    }

    override fun exceptionHandler(handler: Handler<Throwable>?): MessageProducer<T>? {
        throw UnsupportedOperationException()
    }

    override fun address(): String? {
        return buildString {
            senders.forEach {
                append(it.address())
                append(";")
            }
        }
    }

    override fun drainHandler(handler: Handler<Void>?): MessageProducer<T>? {
        throw UnsupportedOperationException()
    }

    override fun deliveryOptions(options: DeliveryOptions?): MessageProducer<T>? {
        senders.forEach { it.deliveryOptions(options) }
        return this
    }

    override fun setWriteQueueMaxSize(maxSize: Int): MessageProducer<T>? {
        throw UnsupportedOperationException()
    }

    override fun writeQueueFull(): Boolean {
        throw UnsupportedOperationException()
    }
}
